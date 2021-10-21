package com.kimxavi87.spring.player;

import com.kimxavi87.spring.player.dto.TeamInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TeamController {
    private final Validator teamValidator;

    @PostMapping("/team")
    public ResponseEntity createTeam(@RequestBody TeamInput teamInput, BindingResult bindingResult) {
        teamValidator.validate(teamInput, bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("team error : {}", teamInput);
            return ResponseEntity.badRequest().build();
        }

        log.info("team success : {}", teamInput);
        return ResponseEntity.ok().build();
    }
}
