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

    /** Size number. */
    public static final int SIZE_30 = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** {@link String} with first name. */
    @NotNull
    @Size(min=2, max=SIZE_30)
    private String firstName;
    /** {@link String} with last name. */
    @NotNull
    @Size(min=2, max=SIZE_30)
    private String lastName;
    /** {@link String} with email. */
    @NotEmpty
    @Email
    private String email;

    @Transient
    @NotEmpty(groups = User.class)
    @Size(min=5, max=SIZE_30)
    private String password;

    @Transient
    @NotEmpty(groups = User.class)
    @Size(min=5, max=SIZE_30)
    private String passwordMatcher;

    private String passwordEncrypted;

    /** {@link String} reset password token. */
    private String resetPasswordToken;
    /** {@link LocalDateTime} reset password expires. */
    private LocalDateTime resetPasswordExpires;

    /** {@link java.lang.Boolean} value about confirmation status. */
    private boolean confirmationStatus;
    /** {@link String} confirmation ID. */
    private String confirmationId;

    /** {@link java.lang.Boolean} value about newsletter status. */
    private boolean newsletterStatus;

    /** {@link String} with provider user ID from social. */
    private String providerUserID;

    /** List of {@link pl.codecouple.omomfood.offers.Offer}'s. */
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="owner")
    private List<Offer> offers;
    /** List of assigned {@link pl.codecouple.omomfood.offers.Offer}'s. */
    @ManyToMany(mappedBy = "assignedUser")
    private List<Offer> assignedOffer;

    /** List of written {@link Reference}'s. */
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="author")
    private List<Reference> writtenReferences;
    /** List of received {@link Reference}'s. */
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="about")
    private List<Reference> receivedReferences;

    /** List of received {@link Message}'s. */
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="recipient")
    private List<Message> receivedMessages;
    /** List of send {@link Message}'s. */
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="author")
    private List<Message> sendMessages;

    @Column
    @Enumerated
    @ElementCollection(targetClass = RoleEnum.class, fetch = FetchType.EAGER)
    private List<RoleEnum> roles;

    @Transient
    private Set<GrantedAuthority> authorities = new HashSet<>();

    /**
     * Constructor of {@link User}.
     *
     * @param firstName the first name.
     * @param lastName the last name.
     * @param email the email.
     * @param password the password.
     * @param passwordMatcher the password matcher.
     * @param roles the roles.
     */
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

    /**
     * Constructor of {@link User}.
     *
     * @param firstName the first name.
     * @param lastName the last name.
     * @param email the email.
     * @param password the password.
     * @param passwordMatcher the password matcher.
     * @param roles the roles.
     * @param providerUserID the provider user id.
     */
    public User(final String firstName,
                final String lastName,
                final String email,
                final String password,
                final String passwordMatcher,
                final List<RoleEnum> roles,
                final String providerUserID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.passwordMatcher = passwordMatcher;
        this.roles = roles;
        this.providerUserID = providerUserID;
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
