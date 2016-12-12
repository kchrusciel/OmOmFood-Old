package pl.codecouple.omomfood.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.web.SignInAdapter;
import pl.codecouple.omomfood.utils.AuthenticateUtils;

import javax.sql.DataSource;

/**
 * Created by Agnieszka on 2016-10-09.
 */
@Configuration
public class SocialConfiguration {


    @Bean
    public SocialConfigurer socialConfigurerAdapter(DataSource dataSource) {
        return new DatabaseSocialConfig(dataSource);
    }

    @Bean
    public SignInAdapter authSignInAdapter() {
        return (userId, connection, request) -> {
            AuthenticateUtils.authenticate(connection);
            return null;
        };
    }
}