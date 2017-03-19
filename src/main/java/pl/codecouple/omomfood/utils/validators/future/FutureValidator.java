package pl.codecouple.omomfood.utils.validators.future;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

/**
 * Created by Krzysztof Chruściel.
 */
public class FutureValidator implements ConstraintValidator<Future, Temporal> {

    @Override
    public void initialize(Future future) {

    }

    @Override
    public boolean isValid(Temporal temporal, ConstraintValidatorContext constraintValidatorContext) {
        if (temporal == null) {
            return true;
        }
        LocalDateTime ld = LocalDateTime.from(temporal);
        if (ld.isAfter(LocalDateTime.now())) {
            return true;
        }
        return false;
    }
}
