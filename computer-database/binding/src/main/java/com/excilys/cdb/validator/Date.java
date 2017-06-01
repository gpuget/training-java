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
    /**
     * Date min year.
     *
     * @return min year
     */
    int min() default 1970;

    /**
     * Date max year.
     *
     * @return max year
     */
    int max() default 2038;

    /**
     * @return message
     */
    String message() default "Date is not valid !";

    /**
     * @return group
     */
    Class<?>[] groups() default {};

    /**
     * @return payload
     */
    Class<? extends Payload>[] payload() default {};
}
