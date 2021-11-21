package com.kimxavi87.spring.palyer;

import com.kimxavi87.spring.player.entity.Member;
import com.kimxavi87.spring.player.reposiotry.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class MemberRepositoryTests {
    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void saveMember() {
        Member member = new Member("Park-ji-sung");
        memberRepository.save(member);

        Optional<Member> byId = memberRepository.findById(member.getId());

        // for logging query
        memberRepository.findAll();

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

    @Test
    public void deleteAllById() {
        List<Member> fromStringSets = createFromStringSets(Set.of("Park-ji-sung", "Son-heung-min", "An-jung-hwan", "xavi", "iniesta"));

        List<Long> deleteIds = Arrays.asList(fromStringSets.get(0).getId(), fromStringSets.get(1).getId());

        List<Member> allByNameIn = memberRepository.findAllByNameIn(Set.of("xavi", "iniesta"));
        System.out.println(allByNameIn.size());

        // delete query 가 ID 당 하나씩 나감
        memberRepository.deleteAllById(deleteIds);

        List<Member> allByNameIn2 = memberRepository.findAllByNameIn(Set.of("xavi", "iniesta"));
        System.out.println(allByNameIn2.size());
    }

    @Test
    public void deleteByNameIn() {
        List<Member> fromStringSets = createFromStringSets(Set.of("Park-ji-sung", "Son-heung-min", "An-jung-hwan", "xavi", "iniesta"));

        List<String> deleteNames= Arrays.asList(fromStringSets.get(0).getName(), fromStringSets.get(1).getName());

        // deleteBy 도 select in 한 다음에 1개씩 삭제
        memberRepository.deleteByNameIn(deleteNames);

        // 여기에 find 안 해주면 delete 쿼리가 아예 처리되지도 않네
        memberRepository.findAll();

    }

    @Test
    public void deleteByWithQueryAnnotation() {
        List<Member> fromStringSets = createFromStringSets(Set.of("Park-ji-sung", "Son-heung-min", "An-jung-hwan", "xavi", "iniesta"));

        List<String> deleteNames= Arrays.asList(fromStringSets.get(0).getName(), fromStringSets.get(1).getName());

        try {
            memberRepository.deleteByNameInWithQueryAnnotation(deleteNames);
        } catch (Exception exception) {
            // @Modifying 안 붙이면 에러 발생
            // java.lang.IllegalStateException: org.hibernate.hql.internal.QueryExecutionRequestException: Not supported for DML operations
            System.out.println(exception.getCause());
        }

        memberRepository.findAll();
    }

    @Test
    public void deleteByWithQueryAndModifyingAnnotation() {
        List<Member> fromStringSets = createFromStringSets(Set.of("Park-ji-sung", "Son-heung-min", "An-jung-hwan", "xavi", "iniesta"));

        List<String> deleteNames= Arrays.asList(fromStringSets.get(0).getName(), fromStringSets.get(1).getName());

        // delete from member where name in ()
        memberRepository.deleteByNameInWithQueryAndModifyingAnnotation(deleteNames);

        memberRepository.findAll();
    }

    @Test
    public void deleteAllBy() {
        List<Member> fromStringSets = createFromStringSets(Set.of("Park-ji-sung", "Son-heung-min", "An-jung-hwan", "xavi", "iniesta"));

        List<String> deleteNames= Arrays.asList(fromStringSets.get(0).getName(), fromStringSets.get(1).getName());

        // ALL 붙여도 차이 없음
        memberRepository.deleteAllByNameIn(deleteNames);

        memberRepository.findAll();

    }

    @Test
    public void customValueGeneratorWithoutFlush() {
        Member member = new Member("Park-ji-sung");
        memberRepository.save(member);

        System.out.println("create time : " + member.getCreateTime());
        Member createdMember = memberRepository.findById(member.getId()).get();

        System.out.println(createdMember.getCreateTime());

        // 영속성 컨텍스트 flush 해주지 않으면 timestamp 값이 채워지지가 않음
        // query 가 안 날라가서 그런 것 같음
        assertEquals(0, createdMember.getCreateTime());
        assertNull(createdMember.getInstant());
    }

    @Test
    public void customValueGenerator() {
        Member member = new Member("Park-ji-sung");
        memberRepository.save(member);

        em.flush();
        em.clear();

        System.out.println("create time : " + member.getCreateTime());
        Member createdMember = memberRepository.findById(member.getId()).get();

        System.out.println(createdMember.getCreateTime());
        assertTrue(createdMember.getCreateTime() > 0 );
    }

    @Test
    public void existsByName() {
        Member member = new Member("Park-ji-sung");
        memberRepository.save(member);

        // select member0_.id as col_0_0_ from member member0_ where member0_.name=? limit 1
        assertThat(memberRepository.existsByName("Park-ji-sung")).isEqualTo(true);
    }

    private List<Member> createFromStringSets(Set<String> strings) {
        List<Member> members = strings.stream()
                .map(Member::new)
                .collect(Collectors.toList());

        memberRepository.saveAll(members);
        return members;
    }
}
