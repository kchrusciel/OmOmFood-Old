package pl.codecouple.omomfood.account.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by krzysztof.chrusciel on 2016-09-09.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
