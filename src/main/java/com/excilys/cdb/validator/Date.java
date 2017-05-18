package com.excilys.cdb.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = DateValidator.class)
@Retention(RUNTIME)
@Target(FIELD)
public @interface Date {
    int min() default 1970;
    int max() default 2038;
    
    String message() default "Date is not valid !";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
