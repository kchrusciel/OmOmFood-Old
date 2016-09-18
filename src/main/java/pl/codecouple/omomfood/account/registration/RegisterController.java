package pl.codecouple.omomfood.account.registration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pl.codecouple.omomfood.account.AccountServiceImpl;
import pl.codecouple.omomfood.account.PasswordService;
import pl.codecouple.omomfood.account.users.User;
import pl.codecouple.omomfood.account.validator.UserValidator;
import pl.codecouple.omomfood.utils.ResourceMessagesService;

import javax.validation.Valid;

/**
 * Created by krzysztof.chrusciel on 2016-07-11.
 */
@Slf4j
@Controller
@RequestMapping("/register")
public class RegisterController extends WebMvcConfigurerAdapter {

    @Autowired
    private EmailService emailService;

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private ResourceMessagesService resourceMessagesService;

    @Autowired
    private UserValidator userValidator;


    @RequestMapping(method = RequestMethod.GET)
    public String showRegisterPage(User userForm){
        log.debug("Show register page");
        return "register";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String registerUser(@Valid User user,
                               BindingResult bindingResult,
                               Model model) {

        log.debug("Register user");
        log.debug("User: " + user);
        log.debug("BindingResult: " + bindingResult);

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors() || !processPasswords(user, bindingResult)) {
            log.debug("Error during register");
            return "register";
        }

        log.debug("Set and send confirmation ID");
        sendConfirmationID(user);

        //TODO change to specific role
        log.debug("Set user Role");
        user.setRoles(accountService.getAllRoles());

        log.debug("Store user in DB");
        accountService.addUser(user);

        model.addAttribute("message", resourceMessagesService.getParametrizedMessages("email.confirm.message", new Object[]{user.getEmail()}));
        return "messages";
    }


    private boolean processPasswords(User user, BindingResult bindingResult) {
        if (!user.getPassword().equals(user.getPasswordMatcher())) {
            bindingResult.rejectValue("password", "Passwords don't match", "Passwords don't match");
            bindingResult.rejectValue("passwordMatcher", "Passwords don't match", "Passwords don't match");
            return false;
        }
        user.setPasswordEncrypted(passwordService.encrypt(user.getPassword()));
        return true;
    }

    private void sendConfirmationID(@Valid User user) {
        user.setConfirmationId(createConfirmationID());
        emailService.sendEmail(resourceMessagesService.getMessage("email.confirm.title"),
                user.getEmail(),
                "http://OmOmFood.pl/confirm?id=" + user.getConfirmationId());
    }


    private String createConfirmationID() {
        return java.util.UUID.randomUUID().toString();
    }

}
