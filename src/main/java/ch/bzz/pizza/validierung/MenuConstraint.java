package ch.bzz.pizza.validierung;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MenuValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MenuConstraint {
    String message() default "Invalid Menu name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}