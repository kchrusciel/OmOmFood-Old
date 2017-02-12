package pl.codecouple.omomfood.account.signup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;
import pl.codecouple.omomfood.account.AccountService;
import pl.codecouple.omomfood.account.authentication.AuthenticateService;
import pl.codecouple.omomfood.account.users.User;
import pl.codecouple.omomfood.utils.ResourceMessagesService;

/**
 * This is {@link SignUpController} for sign up purposes.
 * We got one public endpoint "/signup". GET method takes {@link Connection}
 * object from session. If {@link Connection} exists than provider id is taken.
 * If {@link User} with specific provider exists than enabling is checked.
 * If account is enabled {@link User} is authenticated. In other cases "register" template is showed.
 * If account is disabled page with messages is showed.
 *
 * @author Agnieszka Pieszczek
 */
@Slf4j
@Controller
public class SignUpController {

    /** Template name which is returned when Social {@link User} is not registered.*/
    public static final String TEMPLATE_NAME_REGISTER = "register";

    /** Template name which is returned after registration.*/
    public static final String TEMPLATE_NAME_MESSAGES = "messages";

    /** Model message id.*/
    public static final String MODEL_MESSAGE_ID = "message";

    /** Email confirmation error message id.*/
    public static final String ACCOUNT_DISABLED_MESSAGE_ID = "AbstractUserDetailsAuthenticationProvider.disabled";

    /** Sign up endpoint. */
    public static final String SIGNUP_ENDPOINT = "/signup";

    /** {@link ProviderSignInUtils} provider sign in utils instance. */
    private final ProviderSignInUtils providerSignInUtils;
    /** {@link AuthenticateService} authentication service instance. */
    private final AuthenticateService authenticateService;
    /** {@link AccountService} account service instance. */
    private final AccountService accountService;
    /** {@link ResourceMessagesService} resource messages service instance. */
    private ResourceMessagesService resourceMessagesService;

    /**
     *
     * @param connectionFactoryLocator for {@link ProviderSignInUtils}.
     * @param connectionRepository for {@link ProviderSignInUtils}.
     * @param authenticateService for authentication operations.
     * @param resourceMessagesService for messages operations.
     * @param accountService for account operations.
     */
    @Autowired
    public SignUpController(final ConnectionFactoryLocator connectionFactoryLocator,
                            final UsersConnectionRepository connectionRepository,
                            final AuthenticateService authenticateService,
                            final ResourceMessagesService resourceMessagesService,
                            final AccountService accountService) {
        this.providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
        this.authenticateService = authenticateService;
        this.resourceMessagesService = resourceMessagesService;
        this.accountService = accountService;
    }

    /**
     * This method takes {@link Connection} object from session.
     * If {@link Connection} exists than provider id is taken.
     * If {@link User} with specific provider exists than enabling is checked.
     * If account is enabled {@link User} is authenticated. In other cases "register" template is showed.
     * If account is disabled page with messages is showed.
     * @param request from session.
     * @param registerForm for registration purposes.
     * @param model for data to view.
     * @return <code>String</code> with template name
     */
    @GetMapping(value = SIGNUP_ENDPOINT)
    public String signup(final WebRequest request, final User registerForm, final Model model) {
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
        if (connection != null) {
            String providerUserId = connection.getKey().getProviderUserId();
            if(!authenticateService.isUserExists(providerUserId)){
                registerForm.setFirstName(connection.fetchUserProfile().getFirstName());
                registerForm.setProviderUserID(providerUserId);
                return TEMPLATE_NAME_REGISTER;
            }
            if(!authenticateService.isUserEnabled(providerUserId)){
                model.addAttribute(MODEL_MESSAGE_ID, resourceMessagesService.getMessage(ACCOUNT_DISABLED_MESSAGE_ID));
                return TEMPLATE_NAME_MESSAGES;
            }
            User socialUser = accountService.getUserByProviderUserID(providerUserId);
            authenticateService.authenticate(socialUser.getEmail());
            providerSignInUtils.doPostSignUp(socialUser.getEmail(), request);
        }
        return "redirect:/";
    }
}