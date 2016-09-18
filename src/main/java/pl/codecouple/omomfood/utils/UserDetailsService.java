package pl.codecouple.omomfood.utils;

import pl.codecouple.omomfood.account.users.User;

/**
 * Created by Krzysztof Chruściel.
 */
public interface UserDetailsService {
    User getLoggedUser();
}
