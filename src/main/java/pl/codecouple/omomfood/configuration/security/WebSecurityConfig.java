package pl.codecouple.omomfood.configuration.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import pl.codecouple.omomfood.account.AccountService;
import pl.codecouple.omomfood.account.AccountServiceImpl;

/**
 * Created by krzysztof.chrusciel on 2016-07-08.
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")
                .successHandler(successHandler())
                .and().headers().frameOptions().disable()
                .and().logout().logoutSuccessUrl("/").logoutSuccessHandler(logoutSuccessHandler())
                .and().authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/",
                        "/h2/**",
                        "/register",
                        "/login",
                        "/logout",
                        "/forget-password",
                        "/reset-password",
                        "/offers/**",
                        "/offer/{[0-9]+}",
                        "/confirm",
                        "/signup/**",
                        "/signin/**")
                .permitAll().anyRequest()
                .authenticated()
                .and().csrf().disable();
                 // for h2
        http.exceptionHandling().accessDeniedPage("/403");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return accountService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Handler for stay at the same page after login/logout

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        AuthenticationSuccessHandler handler = new MySimpleUrlAuthenticationSuccessHandler();
//        handler.setUseReferer(true);
        return handler;
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        SimpleUrlLogoutSuccessHandler handler = new SimpleUrlLogoutSuccessHandler();
        handler.setUseReferer(true);
        return handler;
    }

    //End of handler



}
