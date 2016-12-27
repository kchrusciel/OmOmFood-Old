package pl.codecouple.omomfood.configuration.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.Errors;

/**
 * Created by Krzysztof Chruściel.
 */
public class AuthenticationRequiredFieldsException extends AuthenticationException {

    private static final long serialVersionUID = -3613393016881542212L;
    private Errors errors;

    public AuthenticationRequiredFieldsException(String msg, Throwable error) {
        super(msg, error);
    }

    public AuthenticationRequiredFieldsException(String msg) {
        super(msg);
    }

    public AuthenticationRequiredFieldsException(String msg, Errors errors) {
        super(msg);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}