package pl.codecouple.omomfood.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.codecouple.omomfood.account.users.UserRepository;
import pl.codecouple.omomfood.utils.MessagesService;

/**
 * Created by krzysztof.chrusciel on 2016-07-11.
 */
@Slf4j
@Controller
public class OmOmFoodController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessagesService messagesService;

    @RequestMapping("/")
    public String showMainPage() {
        log.debug("USERS:" + userRepository.findAll());
        return "index";
    }

    @RequestMapping("/admin")
    public String showAdminPage() {
        return "admin";
    }

    @RequestMapping("/403")
    public String showAccessDeniedPage(Model model) {
        model.addAttribute("message", messagesService.getMessage("error.access.required"));
        return "messages";
    }

    @RequestMapping("/login")
    public String showLoginPage(Model model) {
        log.debug("Show login page");
        log.debug("BindingResult: " + model);
//        model.addAttribute("message", messagesService.getMessage("error.login.required"));
        return "messages";
    }

}
