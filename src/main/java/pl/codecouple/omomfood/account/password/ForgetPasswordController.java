package pl.codecouple.omomfood.account.password;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Krzysztof Chruściel.
 */
@Controller
public class ForgetPasswordController {

    @GetMapping("/forget-password")
    public String showFogerPasswordPage(){
        return "forget-password";
    }

}
