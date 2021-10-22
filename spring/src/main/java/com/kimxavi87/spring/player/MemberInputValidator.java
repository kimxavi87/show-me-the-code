package com.kimxavi87.spring.player;

import com.kimxavi87.spring.player.dto.MemberInput;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MemberInputValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return MemberInput.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // validate 만 해도 @Valid 된 필드들을 체크할까?
        // 안 함
        MemberInput memberInput = (MemberInput) target;

        System.out.println("MemberInput Validation check : age :" + memberInput.getAge());

        // 다른곳에서 invokeValidator를 쓴 경우 nested value 로 validationUtils 쓰면 안 된다
        // (해결) => nestedPath 사용하면 됨
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name is empty");
    }
}
