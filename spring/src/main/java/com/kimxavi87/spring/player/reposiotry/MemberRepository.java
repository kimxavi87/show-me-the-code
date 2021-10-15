package com.kimxavi87.spring.player.reposiotry;

import com.kimxavi87.spring.player.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface MemberRepository extends CrudRepository<Member, Long> {
    List<Member> findAll(Pageable pageable);
    // name이 아니라 names 로 파라미터 해도 되네 기준이 뭐지, long 같은 것 넣으면 bean 생성 에러 발생
    // @Query 없으면 @Modifying 없어도 쿼리가 나간다
    List<Member> findAllByNameIn(Set<String> names);

    void deleteByNameIn(List<String> names);

    @Query("DELETE FROM Member m WHERE m.name in :names")
    void deleteByNameInWithQueryAnnotation(List<String> names);

    @Modifying
    @Query("DELETE FROM Member m WHERE m.name in :names")
    void deleteByNameInWithQueryAndModifyingAnnotation(List<String> names);
}
