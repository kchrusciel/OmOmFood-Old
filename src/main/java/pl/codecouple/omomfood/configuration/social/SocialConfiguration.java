package pl.codecouple.omomfood.configuration.social;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.web.SignInAdapter;
import pl.codecouple.omomfood.account.authentication.AuthenticateService;

import javax.sql.DataSource;

/**
 * Created by Agnieszka on 2016-10-09.
 */
@Configuration
public class SocialConfiguration {

    @Bean
    public SocialConfigurer socialConfigurerAdapter(final DataSource dataSource) {
        return new DatabaseSocialConfig(dataSource);
    }

    @Bean
    public SignInAdapter authSignInAdapter(final AuthenticateService authenticateService) {
        return (userId, connection, request) -> {
            authenticateService.authenticate(connection.fetchUserProfile().getUsername());
            return null;
        };
    }
}