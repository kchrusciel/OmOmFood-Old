package pl.codecouple.omomfood.account;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.codecouple.omomfood.account.roles.Role;
import pl.codecouple.omomfood.account.users.User;

import java.util.List;

/**
 * Created by Krzysztof Chruściel.
 */
public interface AccountService extends UserDetailsService {

    void addUser(User user);
    User getUserByConfirmationId(String confirmId);
    User getUserByEmail(String email);
    List<Role> getAllRoles();

}
