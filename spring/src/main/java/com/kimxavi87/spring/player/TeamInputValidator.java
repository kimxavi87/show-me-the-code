package com.kimxavi87.spring.player;

import com.kimxavi87.spring.player.dto.TeamInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class TeamInputValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return TeamInput.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        log.info("------------------------------------ validate check");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "teamName", "teamName.empty");
        TeamInput teamInput = (TeamInput) target;

        if (teamInput.getTeamValue() < 1000) {
            errors.rejectValue("teamValue", "too low");
        }
    }
}
