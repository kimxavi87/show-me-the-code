package com.kimxavi87.spring.conf.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PageableValidator.class)
public @interface PageableValid {
    String message() default "Invalid pagination";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int maxPerPage() default 100;
}
