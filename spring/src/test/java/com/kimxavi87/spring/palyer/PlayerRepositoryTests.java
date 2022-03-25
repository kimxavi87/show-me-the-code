package com.kimxavi87.spring.palyer;

import com.kimxavi87.spring.player.entity.Member;
import com.kimxavi87.spring.player.entity.Team;
import com.kimxavi87.spring.player.reposiotry.MemberRepository;
import com.kimxavi87.spring.player.reposiotry.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@Transactional
public class PlayerRepositoryTests {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;

    @PersistenceContext
    EntityManager em;

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

    @Test
    public void fetchOuterJoinPaging() {

        createManyTeamsAndMembers();

        // fetch join과 paging을 같이 쓰면 limit이 먹지 않는다
        // HHH000104: firstResult/maxResults specified with collection fetch; applying in memory!
        // paging을 memory 에서 처리함
        List<Team> allByName = teamRepository.findByName("liverpool", PageRequest.of(0, 10));
        System.out.println(allByName.size());
    }

    @Test
    public void avoidFetchJoinPaging2() {

        createManyTeamsAndMembers();
        em.flush();
        em.clear();

        List<Team> teams = (List<Team>) teamRepository.findAll();
        for (Team team : teams) {
            for (Member member : team.getMembers()) {
                System.out.println(team.getName() + " : " + member.getName());
            }
        }
    }

    @Test
    public void noChangeTeamAndGettingMembers() {
        Team team = new Team("liverpool");
        teamRepository.save(team);

        Member park = Member.builder()
                .name("park-ji-sung")
//                .team(team)
                .build();

        // 아래 연관관계 메소드처럼 team 쪽 member 리스트에 제대로 추가해주지 않으면 안 된다
        park.changeTeam(team);

        memberRepository.save(park);


        // 영속성 컨텍스트 초기화 해도 호출이 안 됨
        // proxy entity 로 생성이 되지 않기 때문에, getter에 의해 lazy loading 같은 호출이 되지 않는 것 같다
        List<Member> members = team.getMembers();
        System.out.println("------- 1 -------");

        members.stream()
                .forEach(m -> System.out.println(m.getName()));

        System.out.println("------- 2 -------");

        List<Team> teamByNames = teamRepository.findByName("liverpool", PageRequest.of(0, 1));
        System.out.println("SIZE : " + teamByNames.size() + " , " + teamByNames.get(0).getMembers().size());
        teamByNames
                .forEach(t -> team.getMembers()
                        .forEach(m -> System.out.println(m.getName())));

        System.out.println("------- 3 -------");

        List<Member> allByNameIn = memberRepository.findAllByNameIn(Set.of("park-ji-sung"));
        allByNameIn.stream().forEach(mm -> System.out.println(mm.getName() + " : " + mm.getTeam().getName()));
    }

    @Test
    public void usingAbstractClass() {
        teamRepository.bulkInsert();
    }

    private void createManyTeamsAndMembers() {
        List<Team> premireTeams = Stream.of("liverpool", "tottenham", "ManU", "Chelsea")
                .map(Team::new)
                .map(team -> teamRepository.save(team))
                .collect(Collectors.toList());

        Member torres = new Member("torres");
        Member park = new Member("park");
        Member gerrard = new Member("gerrard");
        Member lampard = new Member("lampard");
        Member evra = new Member("evra");
        Member son = new Member("son");

        torres.changeTeam(premireTeams.get(0));
        memberRepository.save(torres);

        park.changeTeam(premireTeams.get(2));
        memberRepository.save(park);

        gerrard.changeTeam(premireTeams.get(0));
        memberRepository.save(gerrard);

        lampard.changeTeam(premireTeams.get(3));
        memberRepository.save(lampard);

        evra.changeTeam(premireTeams.get(2));
        memberRepository.save(evra);

        son.changeTeam(premireTeams.get(3));
        memberRepository.save(son);

    }
}
