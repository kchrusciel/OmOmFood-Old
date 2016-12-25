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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.codecouple.omomfood.account.AccountService;
import pl.codecouple.omomfood.account.users.User;
import pl.codecouple.omomfood.email.service.EmailService;
import pl.codecouple.omomfood.utils.ResourceMessagesService;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Slf4j
@Controller
public class ResetPasswordController {

    public static final String RESET_PASSWORD_ENDPOINT = "reset-password";
    public static final String RESET_PASSWORD_TEMPLATE_NAME = "password/reset-password";

    public static final String FORGOT_PASSWORD_WRONG_TOKEN_MESSAGE_ID = "forgot.password.wrong.token";

    public static final String FORGOT_PASSWORD_SUCCESSFULLY_CHANGE_ID = "forgot.password.successfully.changed";

    public static final String MODEL_SUCCESS_MESSAGE_ID = "success";

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

    @GetMapping(RESET_PASSWORD_ENDPOINT)
    public String showResetPasswordPage(final @RequestParam(value = "id") String resetPasswordToken,
                                        final ResetPasswordForm resetPasswordForm,
                                        final Model model){
        log.info("Show reset password page");
        User foundedUser = accountService.findByResetPasswordToken(resetPasswordToken);
        if(foundedUser == null){
            model.addAttribute(MODEL_MESSAGE_ID, resourceMessagesService.getMessage(FORGOT_PASSWORD_WRONG_TOKEN_MESSAGE_ID));
            return TEMPLATE_NAME_MESSAGES;
        }
        if(foundedUser.getResetPasswordExpires().isBefore(LocalDateTime.now())){
            model.addAttribute(MODEL_MESSAGE_ID, resourceMessagesService.getMessage(FORGOT_PASSWORD_WRONG_TOKEN_MESSAGE_ID));
            return TEMPLATE_NAME_MESSAGES;
        }
        model.addAttribute("resetPasswordToken", resetPasswordToken);
        return RESET_PASSWORD_TEMPLATE_NAME;
    }

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

        if (areErrorsAfterResetPasswordFormValidation(resetPasswordForm, bindingResult)) {
            log.error("Error during creating password token");
            return RESET_PASSWORD_TEMPLATE_NAME;
        }

        User userToUpdate = accountService.findByResetPasswordToken(resetPasswordToken);
        if(userToUpdate == null){
            model.addAttribute(MODEL_MESSAGE_ID, resourceMessagesService.getMessage(FORGOT_PASSWORD_WRONG_TOKEN_MESSAGE_ID));
            return TEMPLATE_NAME_MESSAGES;
        }
        resetPasswordToken(userToUpdate);
        setNewEncryptedPasswordPassword(userToUpdate, resetPasswordForm.getPassword());

        model.addAttribute(MODEL_SUCCESS_MESSAGE_ID, resourceMessagesService.getMessage(FORGOT_PASSWORD_SUCCESSFULLY_CHANGE_ID));

        return RESET_PASSWORD_TEMPLATE_NAME;
    }

    private void setNewEncryptedPasswordPassword(final User userToUpdate, final String password) {
        userToUpdate.setPasswordEncrypted(encryptPassword(password));
        accountService.updateUser(userToUpdate);
    }

    private void resetPasswordToken(final User userToUpdate) {
        userToUpdate.setResetPasswordToken(null);
        userToUpdate.setResetPasswordExpires(null);
        accountService.updateUser(userToUpdate);
    }

    private String encryptPassword(final String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    private void validateResetPasswordForm(final ResetPasswordForm resetPasswordForm,
                                            final BindingResult bindingResult) {
        resetPasswordFormValidator.validate(resetPasswordForm, bindingResult);
    }

    /**
     * This method checks if are some errors after forget password
     * form validation.
     *
     * @param resetPasswordForm with values from forget password form.
     * @param bindingResult results from forget password fields.
     * @return <code>true</code> if there are some errors after validation, <code>false</code> otherwise.
     */
    private boolean areErrorsAfterResetPasswordFormValidation(final ResetPasswordForm resetPasswordForm,
                                                              final BindingResult bindingResult) {
        return bindingResult.hasErrors();
    }

}
