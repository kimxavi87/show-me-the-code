package com.kimxavi87.spring.player;

import com.kimxavi87.spring.player.dto.MemberInput;
import com.kimxavi87.spring.player.dto.TeamInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Slf4j
@RequiredArgsConstructor
@Component
public class TeamInputValidator implements Validator {
    private final MemberInputValidator memberInputValidator;

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

        if (teamInput.getMembers() == null) {
            errors.rejectValue("members", "members is null");
        } else if (teamInput.getMembers().size() <= 0) {
            errors.rejectValue("members", "members size 0");
        } else {
            for (MemberInput memberInput : teamInput.getMembers()) {
                ValidationUtils.invokeValidator(memberInputValidator, memberInput, errors);
            }
        }
    }
}
