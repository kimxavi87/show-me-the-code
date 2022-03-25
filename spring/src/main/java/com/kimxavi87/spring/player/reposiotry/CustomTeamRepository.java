package com.kimxavi87.spring.player.reposiotry;

import com.kimxavi87.spring.player.entity.Team;

import java.util.List;

public interface CustomTeamRepository {

    void bulkInsert(List<Team> list);
}
