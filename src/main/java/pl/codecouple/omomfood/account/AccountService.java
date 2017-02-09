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
    User getAdminUser();

    /**
     * This method returns {@link User} which contains the provider user id value in DB.
     *
     * @param providerUserID which is used to search.
     * @return <code>{@link User}</code> founded user.
     */
    User getUserByProviderUserID(String providerUserID);
    List<RoleEnum> getAllRoles();

}
