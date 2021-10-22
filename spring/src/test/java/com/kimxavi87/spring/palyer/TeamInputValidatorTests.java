package com.kimxavi87.spring.palyer;

import com.kimxavi87.spring.player.MemberInputValidator;
import com.kimxavi87.spring.player.TeamInputValidator;
import com.kimxavi87.spring.player.dto.MemberInput;
import com.kimxavi87.spring.player.dto.TeamInput;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamInputValidatorTests {

    @Test
    public void givenCorrectTeamInput_whenCheckValidate_thenIsValid() {
        List<MemberInput> memberInputs = Arrays.asList(new MemberInput("dwpark", 55), new MemberInput("kim", 22));
        TeamInput teamInput = new TeamInput("liverpool", 5000, memberInputs);

        Validator memberInputValidator = new MemberInputValidator();
        Validator teamInputValidator = new TeamInputValidator(memberInputValidator);

        Errors errors = new BeanPropertyBindingResult(teamInput, "teamInput");

        teamInputValidator.validate(teamInput, errors);
        assertThat(errors.hasErrors()).isEqualTo(false);
    }
}
