package pl.codecouple.omomfood.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.codecouple.omomfood.account.AccountService;
import pl.codecouple.omomfood.account.users.User;

/**
 * Created by Krzysztof Chruściel.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    private AccountService accountService;

    @Autowired
    public UserDetailsServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public User getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username
        return accountService.getUserByEmail(name);
    }
}
