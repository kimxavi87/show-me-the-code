package com.kimxavi87.spring.player;

import com.kimxavi87.spring.player.dto.MemberInput;
import com.kimxavi87.spring.player.entity.Member;
import com.kimxavi87.spring.player.reposiotry.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
public class PlayerController {

    private final MemberRepository memberRepository;

    @GetMapping("/member/{id}")
    public ResponseEntity<Member> requestMemberById(@PathVariable("id") @Min(5) long id) {
        return memberRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/members")
    public List<Member> requestMembers(@PageableDefault(size = 10) Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    @PostMapping("/member")
    public ResponseEntity createMember(@Valid @RequestBody MemberInput input) {
        // AllArgsConstructor or NoArgsConstructor 가 없으면 requestbody 자체에서 에러 발생

        log.info("Hello member : {}", input.getName());

        return ResponseEntity.ok()
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
