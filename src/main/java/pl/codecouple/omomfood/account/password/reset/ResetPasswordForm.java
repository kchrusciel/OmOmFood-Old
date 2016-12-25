package pl.codecouple.omomfood.account.password.reset;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * This is {@link ResetPasswordForm} form for password reset purposes.
 *
 * @author Krzysztof Chruściel
 */
@Data
public class ResetPasswordForm {

    /** Password size max. */
    public static final int PASSWORD_SIZE_MAX = 30;
    /** Password size min. */
    public static final int PASSWORD_SIZE_MIN = 5;

    /** Email field in reset password form. */
    @Size(min = PASSWORD_SIZE_MIN, max = PASSWORD_SIZE_MAX)
    @NotEmpty
    private String password;

    /** Email confirmation field in reset password form. */
    @Size(min = PASSWORD_SIZE_MIN, max = PASSWORD_SIZE_MAX)
    @NotEmpty
    private String passwordMatcher;

}
