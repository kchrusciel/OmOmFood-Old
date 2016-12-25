package pl.codecouple.omomfood.account;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.codecouple.omomfood.account.roles.RoleEnum;
import pl.codecouple.omomfood.account.users.User;

import java.util.List;

/**
 * Created by Krzysztof Chruściel.
 */
public interface AccountService extends UserDetailsService {

    void addUser(User user);
    void updateUser(User user);
    User getUserByConfirmationId(String confirmId);
    User getUserByEmail(String email);
    User findByResetPasswordToken(String token);
    List<RoleEnum> getAllRoles();

}
