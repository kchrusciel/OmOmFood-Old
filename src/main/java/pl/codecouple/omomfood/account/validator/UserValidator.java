package pl.codecouple.omomfood.account.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.codecouple.omomfood.account.AccountService;
import pl.codecouple.omomfood.account.users.User;

/**
 * Created by Krzysztof Chruściel.
 */
@Component
public class UserValidator implements Validator {

    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if (accountService.getUserByEmail(user.getUsername()) != null) {
            errors.rejectValue("email", "Duplicate.email");
        }
    }
}
