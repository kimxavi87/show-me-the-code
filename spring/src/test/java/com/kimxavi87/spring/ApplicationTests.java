package com.kimxavi87.spring;

import com.kimxavi87.spring.entity.Member;
import com.kimxavi87.spring.reposiotry.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class ApplicationTests {
    @Autowired
    MemberRepository memberRepository;

    @Test
    void saveMember() {
        Member member = new Member("Park-ji-sung");
        memberRepository.save(member);

        Optional<Member> byId = memberRepository.findById(member.getId());

        // 영속성 컨텍스트 내에서 equals 성립
        assertEquals(member, byId.get());
    }
}
