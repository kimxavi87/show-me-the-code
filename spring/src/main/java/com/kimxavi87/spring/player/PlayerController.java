package com.kimxavi87.spring.player;

import com.kimxavi87.spring.player.entity.Member;
import com.kimxavi87.spring.player.reposiotry.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PlayerController {

    private final MemberRepository memberRepository;

    @GetMapping("/members")
    public List<Member> requestMembers(@PageableDefault(size = 10) Pageable pageable) {
        return memberRepository.findAll(pageable);
    }
}
