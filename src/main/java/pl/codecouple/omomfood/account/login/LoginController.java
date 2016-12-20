package pl.codecouple.omomfood.account.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.codecouple.omomfood.utils.ResourceMessagesService;

/**
 * Created by Krzysztof Chruściel.
 */
@Slf4j
@Controller
public class LoginController {

    /** Template name which is returned after registration.*/
    public static final String TEMPLATE_NAME_MESSAGES = "messages";

    /** {@link ResourceMessagesService} resource messages service instance. */
    private final ResourceMessagesService resourceMessagesService;

    /**
     * Constructor of {@link LoginController} class.
     *
     * @param resourceMessagesService for messages operations.
     *
     */
    public LoginController(final ResourceMessagesService resourceMessagesService) {
        this.resourceMessagesService = resourceMessagesService;
    }

    /**
     *
     * @param modelAndView
     * @param error
     * @param logout
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView showLoginPage(final ModelAndView modelAndView,
                                      final @RequestParam(value = "error", required = false) String error,
                                      final @RequestParam(value = "logout", required = false) String logout) {
        log.info("Show login page");

        if (hasParam(error)) {
            modelAndView.addObject("message",  resourceMessagesService.getMessage("error.invalid.credentials"));
        }
        if (hasParam(logout)) {
            modelAndView.addObject("message", resourceMessagesService.getMessage("logout.message"));
        }
        if (error == null && logout == null) {
            modelAndView.addObject("message", resourceMessagesService.getMessage("error.login.required"));
        }

        modelAndView.addObject("badge", "100");
        modelAndView.setViewName(TEMPLATE_NAME_MESSAGES);

        return modelAndView;
    }

    private boolean hasParam(String param) {
        return param != null;
    }
}
