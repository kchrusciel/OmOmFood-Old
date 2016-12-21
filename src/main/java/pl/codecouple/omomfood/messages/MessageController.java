package pl.codecouple.omomfood.messages;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.codecouple.omomfood.utils.UserDetailsService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Krzysztof Chruściel.
 */
@Slf4j
@Controller
public class MessageController {

    private MessageService messageService;
    private UserDetailsService userDetailsService;

    @Autowired
    public MessageController(MessageService messageService, UserDetailsService userDetailsService) {
        this.messageService = messageService;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(method = RequestMethod.GET,
                    value = "/my/messages")
    public String showMessages(Model model){

        log.debug("Show all user messages");

        Message message = new Message("Some message: " + LocalDateTime.now(), 1l, LocalDateTime.now(), userDetailsService.getLoggedUser(), userDetailsService.getLoggedUser());
        messageService.sendMessage(message);

        List<Message> messages = messageService.getAllMessages(userDetailsService.getLoggedUser());
        model.addAttribute("messages", messages);
        return "account/messages";
    }

    @RequestMapping(method = RequestMethod.GET,
                    value = "/my/messages/{messageId}")
    public String showMessage(final Model model,
                              final @PathVariable long messageId){

        log.debug("Show message by ID");
        log.debug("Message ID:" + messageId);

        Message message = messageService.getMessage(userDetailsService.getLoggedUser(), messageId);

        if(message == null){
            log.debug("Empty message for ID: " + messageId);
            model.addAttribute("messages", "error");
            return "messages";
        }

        log.debug("Founded message:" + message);

        changeStatus(message);
        model.addAttribute("messages", message);
        return "account/message";
    }

    public void changeStatus(final Message message){
        log.debug("Change message status to read");
        if(!message.isRead()){
            message.setRead(true);
        }
    }
}
