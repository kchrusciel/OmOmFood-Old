package pl.codecouple.omomfood.configuration.security;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.codecouple.omomfood.account.AccountService;
import pl.codecouple.omomfood.account.password.forget.ForgetPasswordForm;

/**
 * Created by Krzysztof Chruściel.
 */
@Component
public class LoginFormValidator implements Validator {

    /** {@link AccountService} account service instance. */
    private final AccountService accountService;

    /**
     * Constructor of {@link LoginFormValidator} class.
     *
     * @param accountService for account operations.
     *
     */
    public LoginFormValidator(final AccountService accountService) {
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
