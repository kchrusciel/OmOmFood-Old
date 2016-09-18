package pl.codecouple.omomfood.account.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.codecouple.omomfood.account.users.User;
import pl.codecouple.omomfood.offers.Offer;
import pl.codecouple.omomfood.offers.OfferService;

/**
 * Created by Krzysztof Chruściel.
 */
@Component
public class OfferValidator implements Validator {

    @Autowired
    private OfferService offerService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Offer offer = (Offer) o;

//        if (accountService.getUserByEmail(user.getUsername()) != null) {
//            errors.rejectValue("email", "Duplicate.email");
//        }
    }
}
