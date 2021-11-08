package com.kimxavi87.spring.conf.validator;

import org.springframework.data.domain.Pageable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PageableValidator implements ConstraintValidator<PageableValid, Pageable> {

    private int maxPerPage;

    @Override
    public void initialize(PageableValid constraintAnnotation) {
        this.maxPerPage = constraintAnnotation.maxPerPage();
    }

    @Override
    public boolean isValid(Pageable pageable, ConstraintValidatorContext context) {
        if (pageable.getPageSize() <= maxPerPage) {
            return true;
        }

        return false;
    }
}
