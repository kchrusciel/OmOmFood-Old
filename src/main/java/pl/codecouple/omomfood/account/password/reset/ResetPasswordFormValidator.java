package pl.codecouple.omomfood.account.password.reset;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * This is {@link ResetPasswordFormValidator} validator for password reset purposes.
 * This validator checks {@link ResetPasswordForm}. Checks if user exists in DB.
 *
 * @author Krzysztof Chruściel
 */
@Component
public class ResetPasswordFormValidator implements Validator {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(final Class<?> aClass) {
        return ResetPasswordForm.class.equals(aClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(final Object o, final Errors errors) {
        ResetPasswordForm forgetPasswordForm = (ResetPasswordForm) o;
        if(!forgetPasswordForm.getPassword().equals(forgetPasswordForm.getPasswordMatcher())){
            errors.rejectValue("passwordMatcher", "notMatch.password");
        }
    }
}
