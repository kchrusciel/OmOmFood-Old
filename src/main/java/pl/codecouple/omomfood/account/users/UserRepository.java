package pl.codecouple.omomfood.account.users;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by krzysztof.chrusciel on 2016-09-09.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * This method returns {@link User} which contains email value in DB.
     *
     * @param email which is used to search.
     * @return <code>{@link User}</code> founded user.
     */
    User findByEmail(String email);

    /**
     * This method returns {@link User} which contains confirmation ID value in DB.
     *
     * @param confirmationId which is used to search.
     * @return <code>{@link User}</code> founded user.
     */
    User findByConfirmationId(String confirmationId);

    /**
     * This method returns {@link User} which contains token value in DB.
     *
     * @param token which is used to search.
     * @return <code>{@link User}</code> founded user.
     */
    User findByResetPasswordToken(String token);

    /**
     * This method returns {@link User} which contains the provider user id value in DB.
     *
     * @param providerUserID which is used to search.
     * @return <code>{@link User}</code> founded user.
     */
    User findByProviderUserID(String providerUserID);
}
