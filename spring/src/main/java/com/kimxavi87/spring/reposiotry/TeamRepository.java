package com.kimxavi87.spring.reposiotry;

import com.kimxavi87.spring.entity.Team;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Long> {
}
