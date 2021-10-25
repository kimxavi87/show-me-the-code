package com.kimxavi87.spring.conf.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MobilePhoneNumberValidator.class)
public @interface MobilePhoneNumber {
    // ValidationMessages.properties
    String message() default "{com.kimxavi87.spring.conf.validator.MobilePhoneNumber.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
