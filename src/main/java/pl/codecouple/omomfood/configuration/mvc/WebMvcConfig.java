package pl.codecouple.omomfood.configuration.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pl.codecouple.omomfood.messages.MessageService;
import pl.codecouple.omomfood.utils.UserDetailsService;

/**
 * Created by Krzysztof Chruściel.
 */
@Slf4j
@Configuration
@ComponentScan( basePackages = "pl.codecouple.omomfood" )
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MessageSource messageSource;


    @Bean(name = "messageService")
    public MessagesService messageService() {
        log.debug("Start messageService");
        return () -> "" + messageService.getAllMessages(userDetailsService.getLoggedUser()).size();
    }

    public interface MessagesService {
        String getNumberOfMessages();
    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setValidationMessageSource(messageSource);
        return factory;
    }
}
