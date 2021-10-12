package com.kimxavi87.spring.reposiotry;

import com.kimxavi87.spring.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
}
