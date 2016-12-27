package pl.codecouple.omomfood.account.password.forget;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
 * Created by Krzysztof Chruściel.
 */
@Slf4j
@Controller
public class ForgetPasswordController {

    public static final String FORGET_PASSWORD = "forget-password";
    public static final String FORGET_PASSWORD_TEMPLATE = "password/forget-password";
    public static final int EXPIRATION_DAYS_QUANTITY = 1;

    public static final String MODEL_SUCCESS_MESSAGE_ID = "success";
    public static final String FORGOT_PASSWORD_EMAIL_SUCCESSFULLY_SEND = "forgot.password.email.successfully.send";

    /** Part of reset password link.*/
    public static final String RESET_TOKEN_ID = "reset-password?id=";

    /** {@link EmailService} email service instance. */
    private final EmailService emailService;
    /** {@link AccountService} account service instance. */
    private final AccountService accountService;
    /** {@link ForgetPasswordFormValidator} forget password form validator instance. */
    private final ForgetPasswordFormValidator forgetPasswordFormValidator;
    /** {@link ResourceMessagesService} resource messages service instance. */
    private final ResourceMessagesService resourceMessagesService;

    /**
     *
     * @param emailService
     * @param accountService
     * @param forgetPasswordFormValidator
     * @param resourceMessagesService
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

    @GetMapping(FORGET_PASSWORD)
    public String showForgetPasswordPage(final ForgetPasswordForm forgetPasswordForm){
        log.info("Show forget password page");
        return FORGET_PASSWORD_TEMPLATE;
    }

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

        if (areErrorsAfterForgetPasswordFormValidation(forgetPasswordForm, bindingResult)) {
            log.error("Error during creating password token");
            return FORGET_PASSWORD_TEMPLATE;
        }

        log.info("Get password token");
        String passwordToken = getPasswordResetToken();
        log.debug("Password token: " + passwordToken);

        log.info("Set password token");
        setPasswordToken(passwordToken, forgetPasswordForm);

        log.info("Set password token");
        sendPasswordToken(passwordToken, forgetPasswordForm);

        model.addAttribute(MODEL_SUCCESS_MESSAGE_ID, resourceMessagesService.getMessage(FORGOT_PASSWORD_EMAIL_SUCCESSFULLY_SEND));

        return FORGET_PASSWORD_TEMPLATE;
    }

    private void sendPasswordToken(final String passwordToken,
                                   final ForgetPasswordForm forgetPasswordForm) {
        emailService.sendForgetPasswordEmail(forgetPasswordForm.getEmail(),
                                             RESET_TOKEN_ID + passwordToken);

    }

    private void setPasswordToken(final String passwordToken,
                                  final ForgetPasswordForm forgetPasswordForm) {
        User foundedUser = accountService.getUserByEmail(forgetPasswordForm.getEmail());
        foundedUser.setResetPasswordToken(passwordToken);
        foundedUser.setResetPasswordExpires(getResetPasswordExpireDate());
        accountService.updateUser(foundedUser);
    }

    private LocalDateTime getResetPasswordExpireDate() {
        return LocalDateTime.now().plusDays(EXPIRATION_DAYS_QUANTITY);
    }

    private String getPasswordResetToken() {
        return UUID.randomUUID().toString();
    }


    /**
     * This method checks if are some errors after forget password
     * form validation.
     *
     * @param forgetPasswordForm with values from forget password form.
     * @param bindingResult results from forget password fields.
     * @return <code>true</code> if there are some errors after validation, <code>false</code> otherwise.
     */
    private boolean areErrorsAfterForgetPasswordFormValidation(final ForgetPasswordForm forgetPasswordForm,
                                                               final BindingResult bindingResult) {
        return bindingResult.hasErrors();
    }

    private void validateForgetPasswordForm(final ForgetPasswordForm forgetPasswordForm,
                                            final BindingResult bindingResult) {
        forgetPasswordFormValidator.validate(forgetPasswordForm, bindingResult);
    }

}
