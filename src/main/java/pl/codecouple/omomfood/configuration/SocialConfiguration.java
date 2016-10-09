package pl.codecouple.omomfood.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.web.SignInAdapter;
import pl.codecouple.omomfood.utils.AuthUtil;

/**
 * Created by Agnieszka on 2016-10-09.
 */
@Configuration
public class SocialConfiguration {

    @Bean
    public SignInAdapter authSignInAdapter() {
        return (userId, connection, request) -> {
            AuthUtil.authenticate(connection);
            return null;
        };
    }
}