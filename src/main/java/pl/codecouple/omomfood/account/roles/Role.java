package pl.codecouple.omomfood.account.roles;

import lombok.Data;
import pl.codecouple.omomfood.account.users.User;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by krzysztof.chrusciel on 2016-09-09.
 */
@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    /** {@link User}'s assigned to {@link Role}'s.*/
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    /**
     * Default constructor of {@link Role}.
     */
    public Role() {
    }

    /**
     * Constructor of {@link Role}.
     *
     * @param role enum value.
     */
    public Role(final RoleEnum role) {
        this.role = role;
    }

}
