package pl.codecouple.omomfood.account.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;
import pl.codecouple.omomfood.account.AccountService;
import pl.codecouple.omomfood.account.roles.RoleEnum;
import pl.codecouple.omomfood.account.users.User;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Agnieszka on 2016-10-09.
 */
@Slf4j
@Service
public class AuthenticateServiceImpl implements AuthenticateService {


    private final AccountService accountService;

    @Autowired
    public AuthenticateServiceImpl(final AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void authenticate(final String username) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public boolean isUserExists(final String providerUserId) {
        return Optional.ofNullable(accountService.getUserByProviderUserID(providerUserId)).isPresent();
    }

    @Override
    public boolean isUserEnabled(final String providerUserId) {
        return accountService.getUserByProviderUserID(providerUserId).isEnabled();
    }
}