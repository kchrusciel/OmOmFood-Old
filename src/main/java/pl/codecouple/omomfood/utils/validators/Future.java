package pl.codecouple.omomfood.utils.validators;

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
public @interface Future {

    String message() default "{javax.validation.constraints.Future.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
