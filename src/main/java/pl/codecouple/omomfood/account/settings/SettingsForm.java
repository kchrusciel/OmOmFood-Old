package pl.codecouple.omomfood.account.settings;

import lombok.Data;
import org.hibernate.validator.constraints.Email;


/**
 * This is {@link SettingsFormValidator} form for account settings purposes.
 *
 * @author Krzysztof Chruściel
 */
@Data
public class SettingsForm {

    /** Email field in settings form. */
    @Email
    private String email;
    /** Password field in in settings form. */
    private String password;
    /** First name field in in settings form. */
    private String firstName;
    /** Last name field in in settings form. */
    private String lastName;
    /** Avatar field in in settings form. */
    private String avatarFileName;

}
