package pl.codecouple.omomfood.account.password.forget;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.codecouple.omomfood.account.AccountService;

/**
 * This is {@link ForgetPasswordFormValidator} validator for password reset purposes.
 * This validator checks {@link ForgetPasswordForm}. Checks if user exists in DB.
 *
 * @author Krzysztof Chruściel
 */
@Component
public class ForgetPasswordFormValidator implements Validator {

    /** {@link AccountService} account service instance. */
    private final AccountService accountService;

    /**
     * Constructor of {@link ForgetPasswordFormValidator} class.
     *
     * @param accountService for account operations.
     *
     */
    public ForgetPasswordFormValidator(final AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(final Class<?> aClass) {
        return ForgetPasswordForm.class.equals(aClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(final Object o, final Errors errors) {
        ForgetPasswordForm forgetPasswordForm = (ForgetPasswordForm) o;

        if (accountService.getUserByEmail(forgetPasswordForm.getEmail()) == null) {
            errors.rejectValue("email", "forgot.password.email.not.exists");
        }
    }
}
