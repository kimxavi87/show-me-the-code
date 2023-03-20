package com.kimxavi87.spring.player.controller;

import com.kimxavi87.spring.player.DeleteTeamsRequest;
import com.kimxavi87.spring.player.TeamInputValidator;
import com.kimxavi87.spring.player.dto.TeamInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TeamController {
    private final TeamInputValidator teamInputValidator;

    @PostMapping("/team")
    public ResponseEntity createTeam(@RequestBody TeamInput teamInput, BindingResult bindingResult) {
        teamInputValidator.validate(teamInput, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("team error : {}", teamInput);
            return ResponseEntity.badRequest().build();
        }

        log.info("team success : {}, {}", teamInput, bindingResult);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/team")
    public ResponseEntity<Void> deleteTeams(DeleteTeamsRequest request,
                                            @RequestBody String body) {
        // Param들이 객체로 담아서 온다
        // body도 사용 가능
        log.info("DELETE REQ : {} BODY : {}", request, body);

        return ResponseEntity.ok().build();
    }
}
