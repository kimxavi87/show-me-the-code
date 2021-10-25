package com.kimxavi87.spring.conf.validator;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class MobilePhoneNumberValidator implements ConstraintValidator<MobilePhoneNumber, String> {
    @Override
    public void initialize(MobilePhoneNumber constraintAnnotation) {
        // 데이터 초기화
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        System.out.println("check validation");
        return true;
    }
}
