package pl.codecouple.omomfood.account.users;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.codecouple.omomfood.account.roles.Role;
import pl.codecouple.omomfood.account.roles.RoleEnum;
import pl.codecouple.omomfood.account.users.references.Reference;
import pl.codecouple.omomfood.messages.Message;
import pl.codecouple.omomfood.offers.Offer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by krzysztof.chrusciel on 2016-09-09.
 */
@Slf4j
@NoArgsConstructor
@Data
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min=2,max=30)
    private String firstName;
    @NotNull
    @Size(min=2,max=30)
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    @Transient
    @NotEmpty(groups = User.class)
    @Size(min=5,max=30)
    private String password;

    @Transient
    @NotEmpty(groups = User.class)
    @Size(min=5,max=30)
    private String passwordMatcher;

    private String passwordEncrypted;

    private String resetPasswordToken;
    private LocalDateTime resetPasswordExpires;

    private boolean confirmationStatus;
    private String confirmationId;

    private boolean newsletterStatus;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="owner")
    private List<Offer> offers;

    @ManyToMany(mappedBy = "assignedUser")
    private List<Offer> assignedOffer;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="author")
    private List<Reference> writtenReferences;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="about")
    private List<Reference> references;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="owner")
    private List<Message> messages;

    @Column
    @Enumerated
    @ElementCollection(targetClass = RoleEnum.class, fetch = FetchType.EAGER)
    private List<RoleEnum> roles;

    @Transient
    private Set<GrantedAuthority> authorities = new HashSet<>();

    public User(final String firstName,
                final String lastName,
                final String email,
                final String password,
                final String passwordMatcher,
                final List<RoleEnum> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.passwordMatcher = passwordMatcher;
        this.roles = roles;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isConfirmationStatus();
    }

    @Override
    public String getPassword() {
        return passwordEncrypted;
    }

    public void setPassword(String password) {
        this.password = password;
        this.passwordEncrypted = password;
    }

    public String getFirstAndLastName(){
        return getFirstName() + " " + getLastName();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", passwordEncrypted='" + passwordEncrypted + '\'' +
                ", confirmationStatus=" + confirmationStatus +
                ", confirmationId='" + confirmationId + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
