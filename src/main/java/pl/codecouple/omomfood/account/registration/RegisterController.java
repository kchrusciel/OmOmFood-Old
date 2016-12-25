package pl.codecouple.omomfood.account.registration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pl.codecouple.omomfood.account.AccountServiceImpl;
import pl.codecouple.omomfood.account.PasswordService;
import pl.codecouple.omomfood.email.service.EmailService;
import pl.codecouple.omomfood.account.roles.RoleEnum;
import pl.codecouple.omomfood.account.users.User;
import pl.codecouple.omomfood.account.validator.UserValidator;
import pl.codecouple.omomfood.utils.ResourceMessagesService;

import javax.validation.Valid;
import java.util.Collections;

/**
 * This is {@link RegisterController} for registration purposes.
 * We got one public endpoint "/register". GET method shows registration page.
 * POST method is used for all registration logic.
 * This logic means, form validation, if validation is correctly
 * confirmation id is sets. After that user is stored in DB
 * and confirmation link is send to email address provided in register form.
 *
 * @author Krzysztof Chruściel
 */

@Slf4j
@Controller
@RequestMapping("/register")
public class RegisterController extends WebMvcConfigurerAdapter {

    /** Part of confirmation link.*/
    public static final String CONFIRM_ID = "confirm?id=";
    /** Email confirmation message id.*/
    public static final String EMAIL_CONFIRM_MESSAGE_ID = "email.confirm.message";
    /** Not match password message id.*/
    public static final String NOT_MATCH_PASSWORD_MESSAGE_ID = "notMatch.password";
    /** Model message id.*/
    public static final String MODEL_MESSAGE_ID = "message";

    /** Template name which is returned after registration.*/
    public static final String TEMPLATE_NAME_MESSAGES = "messages";
    /** Template name which is returned on GET method.*/
    public static final String TEMPLATE_NAME_REGISTER = "register";

    /** Password field id from register form.*/
    public static final String PASSWORD_FIELD_ID = "password";
    /** Password matcher field id from register form.*/
    public static final String PASSWORD_MATCHER_FIELD_ID = "passwordMatcher";

    /** {@link EmailService} email service instance. */
    private final EmailService emailService;
    /** {@link AccountServiceImpl} account service instance. */
    private final AccountServiceImpl accountService;
    /** {@link PasswordService} password service instance. */
    private final PasswordService passwordService;
    /** {@link ResourceMessagesService} resource messages service instance. */
    private final ResourceMessagesService resourceMessagesService;
    /** {@link UserValidator} user validator instance. */
    private final UserValidator userValidator;


    /**
     * Constructor of {@link RegisterController} class.
     *
     * @param emailService for email operations.
     * @param accountService for account operations.
     * @param passwordService for password operations.
     * @param resourceMessagesService for messages operations.
     * @param userValidator for register form validation.
     *
     */
    @Autowired
    public RegisterController(final EmailService emailService,
                              final AccountServiceImpl accountService,
                              final PasswordService passwordService,
                              final ResourceMessagesService resourceMessagesService,
                              final UserValidator userValidator) {
        this.emailService = emailService;
        this.accountService = accountService;
        this.passwordService = passwordService;
        this.resourceMessagesService = resourceMessagesService;
        this.userValidator = userValidator;
    }

    /**
     * This is GET "/register" endpoint which is used for
     * register template showing. Also this endpoint
     * bind register form with {@link User}.
     *
     * @param userForm for binding with register form
     * @return <code>String</code> with template name.
     */
    @GetMapping
    public String showRegisterPage(final User userForm){
        log.debug("Show register page");
        return TEMPLATE_NAME_REGISTER;
    }

    /**
     * This is POST "/register" endpoint which is used for
     * user registration. Firstly values from register form are
     * validate
     *
     * @param user with values from registration form.
     * @param bindingResult results from registration form fields.
     * @param model object which takes return values.
     * @return <code>String</code> with template name.
     */
    @PostMapping
    public String registerUser(@Valid final User user,
                               final BindingResult bindingResult,
                               final Model model) {

        log.info("Register user");
        log.debug("User: " + user);
        log.debug("BindingResult: " + bindingResult);

        log.info("Validation register form");
        validateRegisterForm(user, bindingResult);

        if (areErrorsAfterRegisterFormValidation(user, bindingResult)) {
            log.error("Error during register");
            return TEMPLATE_NAME_REGISTER;
        }

        log.info("Set confirmation ID");
        setConfirmationIDToUser(user);

        log.info("Set encrypted password");
        setEncryptedPasswordToUser(user);

        log.info("Set user Role");
        setRolesToUser(user);

        log.info("Store user in DB");
        storeUserInDB(user);

        log.info("Send confirmation ID");
        sendConfirmationIDToUser(user);

        log.info("Bind messages to template");
        bindMessagesToTemplate(user, model);

        return TEMPLATE_NAME_MESSAGES;
    }

    /**
     * This method use {@link UserValidator} for register form validation.
     *
     * @param user with values from registration form.
     * @param bindingResult results from registration form fields.
     */
    private void validateRegisterForm(@Valid final User user, final BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
    }

    /**
     * This method checks if are some errors after registration
     * form validation.
     *
     * @param user with values from registration form.
     * @param bindingResult results from registration form fields.
     * @return <code>true</code> if there are some errors after validation, <code>false</code> otherwise.
     */
    private boolean areErrorsAfterRegisterFormValidation(@Valid final User user, final BindingResult bindingResult) {
        return bindingResult.hasErrors() || !checkPasswords(user, bindingResult);
    }

    /**
     * This method checks if password from registration form are equals.
     *
     * @param user for which password will be checked.
     * @param bindingResult for which errors will be set.
     * @return <code>true</code> if password is checked correctly, <code>false</code> otherwise.
     */
    private boolean checkPasswords(final User user, final BindingResult bindingResult){
        if (!arePasswordsFromRegisterFormEquals(user)) {
            bindingResult.rejectValue(PASSWORD_FIELD_ID, NOT_MATCH_PASSWORD_MESSAGE_ID);
            bindingResult.rejectValue(PASSWORD_MATCHER_FIELD_ID, NOT_MATCH_PASSWORD_MESSAGE_ID);
            return false;
        }
        return true;
    }

    /**
     * This method check if passwords from registration form are equals.
     *
     * @param user for which password will be checked.
     * @return <code>true</code> if passwords are equal, <code>false</code> otherwise.
     */
    private boolean arePasswordsFromRegisterFormEquals(final User user){
        return user.getPassword().equals(user.getPasswordMatcher());
    }

    /**
     * This method is used for confirmation ID setting.
     * This confirmation ID is calculated in createConfirmationID method.
     *
     * @param user for which confirmation ID will be set.
     */
    private void setConfirmationIDToUser(@Valid final User user){
        user.setConfirmationId(createConfirmationID());
    }

    /**
     * This method set encrypted password to {@link User}.
     * This encrypted password is calculated in {@link PasswordService}.
     *
     * @param user for which password will be set.
     */
    private void setEncryptedPasswordToUser(final User user) {
        user.setPasswordEncrypted(passwordService.encrypt(user.getPassword()));
    }

    /**
     * This method sets roles to specified {@link User}.
     * As default {@link User} has {@link RoleEnum#ROLE_USER} value.
     *
     * @param user for which roles will be set.
     */
    private void setRolesToUser(@Valid final User user) {
        user.setRoles(Collections.singletonList(RoleEnum.ROLE_USER));
    }

    /**
     * This method stores {@link User} in DB.
     * For storing this method use {@link AccountServiceImpl}.
     *
     * @param user which will be stored in DB.
     */
    private void storeUserInDB(@Valid final User user) {
        accountService.addUser(user);
    }

    /**
     * This method is used for confirmation ID sending.
     * Confirmation ID is send to email address provided in register form.
     *
     * @param user for which confirmation ID will be send.
     */
    private void sendConfirmationIDToUser(@Valid final User user){
        emailService.sendConfirmationEmail(
                user.getEmail(),
                CONFIRM_ID + user.getConfirmationId());
    }

    /**
     * This method calculate random UUID from {@link java.util.UUID}.
     * This calculated UUID is used as confirmation ID for new {@link User}.
     *
     * @return <code>String</code> with confirmation ID.
     */
    private String createConfirmationID(){
        return java.util.UUID.randomUUID().toString();
    }

    /**
     * This method bind messages to model which is returned
     * after registration complete.
     *
     * @param user from which email will be taken.
     * @param model for which messages will be bind.
     */
    private void bindMessagesToTemplate(@Valid final User user, final Model model) {
        model.addAttribute(MODEL_MESSAGE_ID, resourceMessagesService.getParametrizedMessages(
                EMAIL_CONFIRM_MESSAGE_ID,
                new Object[]{user.getEmail()}));
    }

}
