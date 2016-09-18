package pl.codecouple.omomfood.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Krzysztof Chruściel.
 */
@Slf4j
public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        log.debug("OnAuthenticationSuccess");
        log.debug("httpServletRequest: " + httpServletRequest);
        log.debug("httpServletRequest: " + httpServletRequest.getHeader("referer"));
        log.debug("authentication: " + authentication);
        if(httpServletRequest.getHeader("referer").contains("confirm") || httpServletRequest.getHeader("referer").contains("error"))
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/");
        else
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, httpServletRequest.getHeader("referer"));
    }
}
