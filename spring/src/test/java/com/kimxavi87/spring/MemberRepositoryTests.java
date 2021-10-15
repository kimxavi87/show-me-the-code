package com.kimxavi87.spring;

import com.kimxavi87.spring.player.entity.Member;
import com.kimxavi87.spring.player.reposiotry.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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

    private List<Member> createFromStringSets(Set<String> strings) {
        List<Member> members = strings.stream()
                .map(Member::new)
                .collect(Collectors.toList());

        memberRepository.saveAll(members);
        return members;
    }
}
