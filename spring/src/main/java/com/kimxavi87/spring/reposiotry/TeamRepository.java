package com.kimxavi87.spring.reposiotry;

import com.kimxavi87.spring.entity.Team;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Long> {

    // Pageable 대신에 PageRequest 객체로 넣으니 Error 발생
    @Query("SELECT distinct t FROM Team t left join fetch t.members WHERE t.name = :teamName")
    List<Team> findByName(@Param("teamName") String teamName, Pageable pageable);
}
