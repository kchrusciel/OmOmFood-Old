package pl.codecouple.omomfood.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.codecouple.omomfood.messages.MessageService;
import pl.codecouple.omomfood.utils.UserDetailsService;

/**
 * Created by Krzysztof Chruściel.
 */
@Slf4j
@Configuration
@ComponentScan( basePackages = "pl.codecouple.omomfood" )
public class WebMvcConfig {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserDetailsService userDetailsService;


    @Bean(name = "messageService")
    public MessagesService messageService() {
        log.debug("Start messageService");
        return () -> "" + messageService.getAllMessages(userDetailsService.getLoggedUser()).size();
    }

    public interface MessagesService {
        String getNumberOfMessages();
    }
}
