package pl.codecouple.omomfood.account.registration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.codecouple.omomfood.account.AccountServiceImpl;
import pl.codecouple.omomfood.account.users.User;
import pl.codecouple.omomfood.utils.ResourceMessagesService;

/**
 * Created by krzysztof.chrusciel on 2016-09-09.
 */
@Slf4j
@Controller
public class ConfirmController {

    private final AccountServiceImpl accountService;

    private final ResourceMessagesService resourceMessagesService;

    private final EmailService emailService;

    @Autowired
    public ConfirmController(AccountServiceImpl accountService, ResourceMessagesService resourceMessagesService, EmailService emailService) {
        this.accountService = accountService;
        this.resourceMessagesService = resourceMessagesService;
        this.emailService = emailService;
    }

    @RequestMapping("/confirm")
    public String confirmID(@RequestParam(value = "id") String confirmId,
                            Model model){

        log.debug("Start confirmation");
        log.debug("Confirm ID:" + confirmId);

        User user = accountService.getUserByConfirmationId(confirmId);

        String message = activeUser(user);

        model.addAttribute("message", message);
        return "messages";
    }

    public String activeUser(User user){
        if(user == null){
            return resourceMessagesService.getMessage("email.confirm.error");
        }

        log.debug("Found user:" + user);

        if(!user.isConfirmationStatus()){
            user.setConfirmationStatus(true);
            user.setConfirmationId(null);
            accountService.addUser(user);
            emailService.sendWelcomeEmail(user.getEmail());
        }

        return resourceMessagesService.getParametrizedMessages("email.confirmed.message", new Object[]{user.getUsername()});
    }

}
