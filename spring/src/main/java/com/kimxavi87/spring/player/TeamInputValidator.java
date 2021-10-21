package com.kimxavi87.spring.player;

import com.kimxavi87.spring.player.dto.TeamInput;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TeamInputValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return TeamInput.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
