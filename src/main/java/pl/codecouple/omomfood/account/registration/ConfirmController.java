package pl.codecouple.omomfood.account.registration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.codecouple.omomfood.account.AccountServiceImpl;
import pl.codecouple.omomfood.account.users.User;
import pl.codecouple.omomfood.utils.MessagesService;

/**
 * Created by krzysztof.chrusciel on 2016-09-09.
 */
@Slf4j
@Controller
public class ConfirmController {

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private MessagesService messagesService;

    @RequestMapping("/confirm")
    public String confirmID(@RequestParam(value = "id") String confirmId,
                            Model model){

        log.debug("Start confirmation");
        log.debug("Confirm ID:" + confirmId);

        User user = accountService.getUserByConfirmationId(confirmId);

        String message = "Invalid confirmation id. Contact us or try again.";
        if(user!=null){

            log.debug("Found user:" + user);

            if(!user.isConfirmationStatus()){
                user.setConfirmationStatus(true);
                user.setConfirmationId(null);
                accountService.addUser(user);
            }
            message = user.getUsername() + ", your account has been verified. You may now log in. ";
        }

        log.debug("No user found during confirmation");

        model.addAttribute("message", message);
        return "messages";
    }

}
