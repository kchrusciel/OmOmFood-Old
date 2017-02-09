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
 * Created by Agnieszka on 2016-10-09.
 */
@Slf4j
@Controller
public class SignUpController {

    /** Template name which is returned on GET method.*/
    public static final String TEMPLATE_NAME_REGISTER = "register";

    /** Template name which is returned after registration.*/
    public static final String TEMPLATE_NAME_MESSAGES = "messages";

    /** Model message id.*/
    public static final String MODEL_MESSAGE_ID = "message";

    /** Email confirmation error message id.*/
    public static final String ACCOUNT_DISABLED_MESSAGE_ID = "AbstractUserDetailsAuthenticationProvider.disabled";

    private final ProviderSignInUtils providerSignInUtils;
    private final AuthenticateService authenticateService;

    private final AccountService accountService;

    /** {@link ResourceMessagesService} resource messages service instance. */
    private ResourceMessagesService resourceMessagesService;

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

    @GetMapping(value = "/signup")
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