package pl.codecouple.omomfood.configuration.security;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Krzysztof Chruściel.
 */
@Data
public class LoginForm {

    @NotEmpty
    private String email;
    @NotEmpty
    @Length(min=5,max=60)
    private String password;

}
