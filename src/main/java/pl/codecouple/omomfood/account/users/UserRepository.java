package pl.codecouple.omomfood.account.users;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by krzysztof.chrusciel on 2016-09-09.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByConfirmationId(String confirmationId);
}
