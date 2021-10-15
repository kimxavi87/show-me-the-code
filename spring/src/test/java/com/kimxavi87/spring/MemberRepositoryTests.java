package com.kimxavi87.spring;

import com.kimxavi87.spring.player.entity.Member;
import com.kimxavi87.spring.player.reposiotry.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class MemberRepositoryTests {
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void saveMember() {
        Member member = new Member("Park-ji-sung");
        memberRepository.save(member);

        Optional<Member> byId = memberRepository.findById(member.getId());

        // 영속성 컨텍스트 내에서 equals 성립
        assertEquals(member, byId.get());
    }

    @Test
    public void findByPaging() {
        createFromStringSets(Set.of("Park-ji-sung", "Son-heung-min", "An-jung-hwan", "xavi", "iniesta"));

        List<Member> all = memberRepository.findAll(PageRequest.of(0, 2));
        System.out.println(all.size());

        List<Member> all2 = (List<Member>) memberRepository.findAll();
        System.out.println(all2.size());
    }

    @Test
    public void canSelectWithSet() {
        createFromStringSets(Set.of("Park-ji-sung", "Son-heung-min", "An-jung-hwan", "xavi", "iniesta"));

        List<Member> allByNameIn = memberRepository.findAllByNameIn(Set.of("xavi", "iniesta"));
        System.out.println(allByNameIn.size());
    }

    private List<Member> createFromStringSets(Set<String> strings) {
        List<Member> members = strings.stream()
                .map(Member::new)
                .collect(Collectors.toList());

        memberRepository.saveAll(members);
        return members;
    }
}
