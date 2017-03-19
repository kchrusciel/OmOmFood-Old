package pl.codecouple.omomfood.utils.validators.price;

import pl.codecouple.omomfood.utils.validators.future.FutureValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by Krzysztof Chruściel.
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureValidator.class)
@Documented
public @interface Price {

    String message() default "{javax.validation.constraints.Price.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
