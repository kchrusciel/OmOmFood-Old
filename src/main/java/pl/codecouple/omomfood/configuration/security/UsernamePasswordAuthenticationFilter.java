package pl.codecouple.omomfood.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Krzysztof Chruściel.
 */
public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private LoginFormValidator loginFormValidator;

    private String usernameParameter = "username";
    private String passwordParameter = "password";

    private boolean postOnly = true;

    public UsernamePasswordAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if(this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            LoginForm loginForm = new LoginForm();
            String username = this.obtainUsername(request);
            String password = this.obtainPassword(request);
            if(username == null) {
                username = "";
            }

            if(password == null) {
                password = "";
            }

            loginForm.setEmail(request.getParameter("username"));
            loginForm.setPassword(request.getParameter("password"));

            Errors errors = new BeanPropertyBindingResult(loginForm, "login");

            if(!this.isValid(loginFormValidator, errors, loginForm)) {
                throw new AuthenticationRequiredFieldsException("validation failed", errors);
            }

            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected boolean isValid(LoginFormValidator loginFormValidator, Errors errors, LoginForm loginForm) {
        loginFormValidator.validate(loginForm, errors);
        return errors.hasErrors();
    }

    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(this.passwordParameter);
    }

    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(this.usernameParameter);
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }


}
