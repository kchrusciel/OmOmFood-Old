package pl.codecouple.omomfood.account.password.reset;

/**
 * Created by Krzysztof Chruściel.
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.codecouple.omomfood.account.AccountService;
import pl.codecouple.omomfood.account.password.forget.ForgetPasswordController;
import pl.codecouple.omomfood.account.users.User;
import pl.codecouple.omomfood.email.service.EmailService;
import pl.codecouple.omomfood.utils.ResourceMessagesService;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Slf4j
@Controller
public class ResetPasswordController {

    /** Reset-password endpoint name .*/
    private static final String RESET_PASSWORD_ENDPOINT = "reset-password";
    /** Reset-password template name .*/
    private static final String RESET_PASSWORD_TEMPLATE_NAME = "password/reset-password";
    /** Wrong token message ID .*/
    private static final String FORGOT_PASSWORD_WRONG_TOKEN_MESSAGE_ID = "forgot.password.wrong.token";
    /** Expired token message ID .*/
    private static final String FORGOT_PASSWORD_EXPIRED_TOKEN_MESSAGE_ID = "forgot.password.expired.token";
    /** Successfully change token message ID .*/
    private static final String FORGOT_PASSWORD_SUCCESSFULLY_CHANGE_ID = "forgot.password.successfully.changed";
    /** Reset-password successfully model ID .*/
    private static final String MODEL_SUCCESS_MESSAGE_ID = "success";

    /** Template message name.*/
    public static final String TEMPLATE_NAME_MESSAGES = "messages";

    /** Model message id.*/
    public static final String MODEL_MESSAGE_ID = "message";

    /** {@link AccountService} account service instance. */
    private final AccountService accountService;
    /** {@link ResourceMessagesService} resource messages service instance. */
    private final ResourceMessagesService resourceMessagesService;
    /** {@link EmailService} email service instance. */
    private final EmailService emailService;
    /** {@link ResetPasswordFormValidator} reset password form validator instance. */
    private final ResetPasswordFormValidator resetPasswordFormValidator;

    /**
     * Constructor of {@link ResetPasswordController} class.
     *
     * @param emailService for email operations.
     * @param accountService for account operations.
     * @param resourceMessagesService for messages operations.
     * @param resetPasswordFormValidator for form validation.
     */
    @Autowired
    public ResetPasswordController(final AccountService accountService,
                                   final ResourceMessagesService resourceMessagesService,
                                   final EmailService emailService,
                                   final ResetPasswordFormValidator resetPasswordFormValidator) {
        this.accountService = accountService;
        this.resourceMessagesService = resourceMessagesService;
        this.emailService = emailService;
        this.resetPasswordFormValidator = resetPasswordFormValidator;
    }

    /**
     * This is GET {@link ResetPasswordController#RESET_PASSWORD_ENDPOINT} endpoint which checks password token.
     * If password token is correct {@link ResetPasswordController#RESET_PASSWORD_TEMPLATE_NAME} is showed.
     *
     * @param resetPasswordToken from {@link User}.
     * @param resetPasswordForm for binding with reset password form.
     * @param model object which takes return values.
     * @return <code>{@link String}</code> with template name.
     */
    @GetMapping(RESET_PASSWORD_ENDPOINT)
    public String showResetPasswordPage(final @RequestParam(value = "id") String resetPasswordToken,
                                        final ResetPasswordForm resetPasswordForm,
                                        final Model model){
        log.info("Show reset password page");
        User foundedUser = accountService.findByResetPasswordToken(resetPasswordToken);
        if(foundedUser == null){
            model.addAttribute(MODEL_MESSAGE_ID,
                    resourceMessagesService.getParametrizedMessages(FORGOT_PASSWORD_WRONG_TOKEN_MESSAGE_ID,  new Object[]{ForgetPasswordController.FORGET_PASSWORD}));
            return TEMPLATE_NAME_MESSAGES;
        }
        if(isPasswordExpired(foundedUser)){
            model.addAttribute(MODEL_MESSAGE_ID,
                    resourceMessagesService.getParametrizedMessages(FORGOT_PASSWORD_EXPIRED_TOKEN_MESSAGE_ID, new Object[]{ForgetPasswordController.FORGET_PASSWORD}));
            return TEMPLATE_NAME_MESSAGES;
        }
        model.addAttribute("resetPasswordToken", resetPasswordToken);
        return RESET_PASSWORD_TEMPLATE_NAME;
    }

    /**
     * This method check if password token is expired.
     *
     * @param userToCheck for which expired token will be checked.
     * @return <code>true</code> if password expire, <code>false</code> otherwise.
     */
    private boolean isPasswordExpired(final User userToCheck) {
        return userToCheck.getResetPasswordExpires().isBefore(LocalDateTime.now());
    }

    /**
     *
     * @param resetPasswordForm
     * @param resetPasswordToken
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping(RESET_PASSWORD_ENDPOINT)
    public String resetPassword(final @Valid ResetPasswordForm resetPasswordForm,
                                final @RequestParam(value = "id") String resetPasswordToken,
                                final BindingResult bindingResult,
                                final Model model){
        log.info("Reset password");
        log.debug("ResetPasswordForm: " + resetPasswordForm);
        log.debug("BindingResult: " + bindingResult);

        if(bindingResult.hasErrors()){
            log.error("Error during creating password token");
            return RESET_PASSWORD_TEMPLATE_NAME;
        }

        validateResetPasswordForm(resetPasswordForm, bindingResult);

        if (areErrorsAfterResetPasswordFormValidation(bindingResult)) {
            log.error("Error during creating password token");
            return RESET_PASSWORD_TEMPLATE_NAME;
        }

        User userToUpdate = accountService.findByResetPasswordToken(resetPasswordToken);
        if(userToUpdate == null){
            model.addAttribute(MODEL_MESSAGE_ID,
                    resourceMessagesService.getMessage(FORGOT_PASSWORD_WRONG_TOKEN_MESSAGE_ID));
            return TEMPLATE_NAME_MESSAGES;
        }
        resetPasswordToken(userToUpdate);
        setNewEncryptedPasswordPassword(userToUpdate, resetPasswordForm.getPassword());

        model.addAttribute(MODEL_SUCCESS_MESSAGE_ID,
                resourceMessagesService.getMessage(FORGOT_PASSWORD_SUCCESSFULLY_CHANGE_ID));

        return RESET_PASSWORD_TEMPLATE_NAME;
    }

    /**
     * This method use {@link ResetPasswordForm} for reset password form validation.
     *
     * @param resetPasswordForm with values from reset password form.
     * @param bindingResult results from reset password form fields.
     */
    private void validateResetPasswordForm(final ResetPasswordForm resetPasswordForm,
                                           final BindingResult bindingResult) {
        resetPasswordFormValidator.validate(resetPasswordForm, bindingResult);
    }

    /**
     * This method set password to {@link User}. This password will be encrypted
     * using {@link ResetPasswordController#encryptPassword(String)}.
     *
     * @param userToUpdate for which password will be updated.
     * @param password value which will be updated on {@link User}
     */
    private void setNewEncryptedPasswordPassword(final User userToUpdate, final String password) {
        userToUpdate.setPasswordEncrypted(encryptPassword(password));
        accountService.updateUser(userToUpdate);
    }

    /**
     * This method encrypt new password using {@link BCryptPasswordEncoder}.
     *
     * @param password which will be enrypted.
     * @return <code>{@link String}</code> with encrypted password.
     */
    private String encryptPassword(final String password){
        return new BCryptPasswordEncoder().encode(password);
    }

    /**
     * This method reset password token on {@link User}.
     * Reset means set {@link User#resetPasswordExpires} and {@link User#resetPasswordToken} to null.
     *
     * @param userToUpdate for which password token will be reset.
     */
    private void resetPasswordToken(final User userToUpdate) {
        userToUpdate.setResetPasswordToken(null);
        userToUpdate.setResetPasswordExpires(null);
        accountService.updateUser(userToUpdate);
    }


    /**
     * This method checks if are some errors after reset password form validation.
     *
     * @param bindingResult results from reset password fields.
     * @return <code>true</code> if there are some errors after validation, <code>false</code> otherwise.
     */
    private boolean areErrorsAfterResetPasswordFormValidation(final BindingResult bindingResult) {
        return bindingResult.hasErrors();
    }

}
