package pl.codecouple.omomfood.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.codecouple.omomfood.utils.ResourceMessagesService;

/**
 * Created by krzysztof.chrusciel on 2016-07-11.
 */
@Slf4j
@Controller
public class OmOmFoodController {

    @Autowired
    private ResourceMessagesService resourceMessagesService;

    @RequestMapping("/")
    public String showMainPage() {
        return "index";
    }

    @RequestMapping("/admin")
    public String showAdminPage() {
        return "admin";
    }

    @RequestMapping("/403")
    public String showAccessDeniedPage(Model model) {
        model.addAttribute("message", resourceMessagesService.getMessage("error.access.required"));
        return "messages";

    }

    @RequestMapping("/messages")
    public String showAccessMessagesPage() {
        return "messages";
    }


}
