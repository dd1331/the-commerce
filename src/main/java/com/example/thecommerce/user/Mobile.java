package com.example.thecommerce.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

@Pattern(regexp = "^\\d{2,3}\\d{3,4}\\d{4}$", message = "01000000000 형식으로만 가능합니다")
@Constraint(validatedBy = {})
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Mobile {

    String message() default "01000000000 형식으로만 가능합니다";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
