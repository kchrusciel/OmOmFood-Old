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
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Role";
    }
}
