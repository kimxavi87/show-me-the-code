package com.kimxavi87.spring.player.reposiotry;

import org.springframework.stereotype.Repository;

@Repository
public class CustomTeamRepositoryImpl implements CustomTeamRepository {
    @Override
    public void bulkInsert() {
        System.out.println("bulk insert");
    }
}
