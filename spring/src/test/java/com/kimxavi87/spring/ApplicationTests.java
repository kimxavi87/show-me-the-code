package com.kimxavi87.spring;

import com.kimxavi87.spring.entity.Member;
import com.kimxavi87.spring.entity.Team;
import com.kimxavi87.spring.reposiotry.MemberRepository;
import com.kimxavi87.spring.reposiotry.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class ApplicationTests {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;

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
        List<Member> members = Stream.of("Park-ji-sung", "Son-heung-min", "An-jung-hwan", "xavi", "iniesta")
                .map(Member::new)
                .collect(Collectors.toList());

        memberRepository.saveAll(members);

        List<Member> all = memberRepository.findAll(PageRequest.of(0, 2));
        System.out.println(all.size());

        List<Member> all2 = (List<Member>) memberRepository.findAll();
        System.out.println(all2.size());
    }

    @Test
    public void memberWithTeam() {
        Member member = new Member("Park-ji-sung");
        Team team = new Team("Manchester-UTD");
        member.changeTeam(team);

        memberRepository.save(member);

        Assertions.assertNull(team.getId());

        teamRepository.save(team);

        Assertions.assertNotNull(team.getId());

        Optional<Team> byId = teamRepository.findById(team.getId());
        Team team1 = byId.get();
        Assertions.assertEquals(team1.getMembers().size(), 1);

        Optional<Member> byId1 = memberRepository.findById(member.getId());
        Member member1 = byId1.get();
        Team team2 = member1.getTeam();

        Assertions.assertEquals(team, team2);
    }

}
