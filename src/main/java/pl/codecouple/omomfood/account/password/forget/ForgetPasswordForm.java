package pl.codecouple.omomfood.account.password.forget;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * This is {@link ForgetPasswordForm} form for password reset purposes.
 *
 * @author Krzysztof Chruściel
 */
@Data
public class ForgetPasswordForm {

    /** Email field in forget password form. */
    @Email
    @NotEmpty
    private String email;

}
