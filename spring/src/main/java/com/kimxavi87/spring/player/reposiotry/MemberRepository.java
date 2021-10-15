package com.kimxavi87.spring.player.reposiotry;

import com.kimxavi87.spring.player.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface MemberRepository extends CrudRepository<Member, Long> {
    List<Member> findAll(Pageable pageable);
    List<Member> findAllByNameIn(Set<String> names);
}
