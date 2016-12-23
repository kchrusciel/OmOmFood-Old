package pl.codecouple.omomfood.account.password;

/**
 * Created by Krzysztof Chruściel.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResetPasswordController {

    @GetMapping("/reset-password")
    public String resetPassword(){
        return "reset-password";
    }

}
