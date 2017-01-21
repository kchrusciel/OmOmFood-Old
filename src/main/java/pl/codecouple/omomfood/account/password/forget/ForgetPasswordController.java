package pl.codecouple.omomfood.account.password.forget;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.codecouple.omomfood.account.AccountService;
import pl.codecouple.omomfood.account.users.User;
import pl.codecouple.omomfood.email.service.EmailService;
import pl.codecouple.omomfood.utils.ResourceMessagesService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This is {@link ForgetPasswordController} for forget password purposes.
 * We got one public endpoint "/forget-password". GET method shows forget password page.
 * POST method is used for restore password logic.
 * This logic means, form validation, if validation is correctly
 * reset password token is generated. After that, this token is set in {@link User} and is stored in DB
 * Reset password link is send to email address provided in reset password form.
 *
 * @author Krzysztof Chruściel
 */

@Slf4j
@Controller
public class ForgetPasswordController {

    /** Forget-password endpoint name .*/
    private static final String FORGET_PASSWORD = "forget-password";
    /** Forget-password template name .*/
    private static final String FORGET_PASSWORD_TEMPLATE = "password/forget-password";
    /** Password expiration days quantity .*/
    private static final int PASSWORD_EXPIRATION_DAYS_QUANTITY = 1;

    /** Forget-password successfully model ID .*/
    private static final String MODEL_SUCCESS_MESSAGE_ID = "success";
    /** Forget-password successfully message ID .*/
    private static final String FORGOT_PASSWORD_EMAIL_SUCCESSFULLY_SEND = "forgot.password.email.successfully.send";

    /** Part of reset password link.*/
    private static final String RESET_TOKEN_ID = "reset-password?id=";

    /** {@link EmailService} email service instance. */
    private final EmailService emailService;
    /** {@link AccountService} account service instance. */
    private final AccountService accountService;
    /** {@link ForgetPasswordFormValidator} forget password form validator instance. */
    private final ForgetPasswordFormValidator forgetPasswordFormValidator;
    /** {@link ResourceMessagesService} resource messages service instance. */
    private final ResourceMessagesService resourceMessagesService;

    /**
     * Constructor of {@link ForgetPasswordController} class.
     *
     * @param emailService for email operations.
     * @param accountService for account operations.
     * @param forgetPasswordFormValidator for forget password form validation.
     * @param resourceMessagesService for messages operations.
     */
    @Autowired
    public ForgetPasswordController(final EmailService emailService,
                                    final AccountService accountService,
                                    final ForgetPasswordFormValidator forgetPasswordFormValidator,
                                    final ResourceMessagesService resourceMessagesService) {
        this.emailService = emailService;
        this.accountService = accountService;
        this.forgetPasswordFormValidator = forgetPasswordFormValidator;
        this.resourceMessagesService = resourceMessagesService;
    }

    /**
     * This is GET "/forget-password" endpoint which is used for
     * forget password template showing. Also this endpoint
     * bind forget password form with {@link ForgetPasswordForm}.
     *
     * @param forgetPasswordForm for binding with forget password form.
     * @return <code>String</code> with template name.
     */
    @GetMapping(FORGET_PASSWORD)
    public String showForgetPasswordPage(final ForgetPasswordForm forgetPasswordForm){
        log.info("Show forget password page");
        return FORGET_PASSWORD_TEMPLATE;
    }

    /**
     * This is POST "/forget-password" endpoint which is used for
     * reset password. Firstly values from forget password form are
     * validate if validation is correctly reset password token is generated.
     * After that, this token is set in {@link User} and is stored in DB.
     * Reset password link is send to email address provided in reset password form.
     *
     * @param forgetPasswordForm with values from forget password from.
     * @param bindingResult results from forget password from fields.
     * @param model object which takes return values.
     * @return <code>{@link String}</code> with template name.
     */
    @PostMapping(FORGET_PASSWORD)
    public String createPasswordToken(final @Valid ForgetPasswordForm forgetPasswordForm,
                                      final BindingResult bindingResult,
                                      final Model model){
        log.info("Create Password Token");
        log.debug("ForgetPasswordForm: " + forgetPasswordForm);
        log.debug("BindingResult: " + bindingResult);

        if(bindingResult.hasErrors()){
            log.error("Error during creating password token");
            return FORGET_PASSWORD_TEMPLATE;
        }

        validateForgetPasswordForm(forgetPasswordForm, bindingResult);

        if (areErrorsAfterForgetPasswordFormValidation(bindingResult)) {
            log.error("Error during creating password token");
            return FORGET_PASSWORD_TEMPLATE;
        }

        log.info("Get password token");
        String passwordToken = getPasswordResetToken();
        log.debug("Password token: " + passwordToken);

        log.info("Set password token");
        setPasswordToken(passwordToken, forgetPasswordForm);

        log.info("Send password token");
        sendPasswordToken(passwordToken, forgetPasswordForm);

        model.addAttribute(MODEL_SUCCESS_MESSAGE_ID,
                resourceMessagesService.getMessage(FORGOT_PASSWORD_EMAIL_SUCCESSFULLY_SEND));

        return FORGET_PASSWORD_TEMPLATE;
    }

    /**
     * This method use {@link ForgetPasswordFormValidator} for forget password form validation.
     *
     * @param forgetPasswordForm with values from forget password form.
     * @param bindingResult results from forget password form fields.
     */
    private void validateForgetPasswordForm(final ForgetPasswordForm forgetPasswordForm,
                                            final BindingResult bindingResult) {
        forgetPasswordFormValidator.validate(forgetPasswordForm, bindingResult);
    }

    /**
     * This method checks if are some errors after forget password form validation.
     *
     * @param bindingResult results from forget password fields.
     * @return <code>true</code> if there are some errors after validation, <code>false</code> otherwise.
     */
    private boolean areErrorsAfterForgetPasswordFormValidation(final BindingResult bindingResult) {
        return bindingResult.hasErrors();
    }

    /**
     * This method create password reset token using {@link UUID#randomUUID()}.
     *
     * @return <code>{@link String}</code> with password reset token.
     */
    private String getPasswordResetToken() {
        return UUID.randomUUID().toString();
    }

    /**
     * This method sets password token and password expiration date on founded user.
     *
     * @param passwordToken which will be stored on {@link User}.
     * @param forgetPasswordForm forget password form with email address.
     */
    private void setPasswordToken(final String passwordToken,
                                  final ForgetPasswordForm forgetPasswordForm) {
        User foundedUser = accountService.getUserByEmail(forgetPasswordForm.getEmail());
        foundedUser.setResetPasswordToken(passwordToken);
        foundedUser.setResetPasswordExpires(getResetPasswordExpireDate());
        accountService.updateUser(foundedUser);
    }

    /**
     * This method create {@link LocalDateTime} object with password expiration date.
     * Current date is increased by {@link ForgetPasswordController#PASSWORD_EXPIRATION_DAYS_QUANTITY}.
     *
     * @return <code>{@link LocalDateTime}</code> with password expiration date.
     */
    private LocalDateTime getResetPasswordExpireDate() {
        return LocalDateTime.now().plusDays(PASSWORD_EXPIRATION_DAYS_QUANTITY);
    }

    /**
     * This method send {@link pl.codecouple.omomfood.email.templates.forget.password.ForgetPasswordEmail}
     * with password token to address email given in forget password form.
     *
     * @param passwordToken which will be send to email address.
     * @param forgetPasswordForm forget password form with email address.
     */
    private void sendPasswordToken(final String passwordToken,
                                   final ForgetPasswordForm forgetPasswordForm) {
        emailService.sendForgetPasswordEmail(forgetPasswordForm.getEmail(),
                                             RESET_TOKEN_ID + passwordToken);

    }

}
