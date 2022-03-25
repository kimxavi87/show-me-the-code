package com.kimxavi87.spring.player.reposiotry;

import com.kimxavi87.spring.player.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class CustomTeamRepositoryImpl implements CustomTeamRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void bulkInsert(List<Team> list) {
        if (list.isEmpty()) {
            return;
        }

        jdbcTemplate.batchUpdate("INSERT INTO team (`id`, `name`) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        Team entity = list.get(i);
                        ps.setLong(1, entity.getId());
                        ps.setString(2, entity.getName());
                    }

                    @Override
                    public int getBatchSize() {
                        return list.size();
                    }
                });
    }
}
