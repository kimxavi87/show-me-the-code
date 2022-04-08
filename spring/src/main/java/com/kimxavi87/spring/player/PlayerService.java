package com.kimxavi87.spring.player;

import com.kimxavi87.spring.player.dto.MemberInput;
import com.kimxavi87.spring.player.entity.Team;
import com.kimxavi87.spring.player.reposiotry.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Validated
@Service
public class PlayerService {
    private final TeamRepository teamRepository;

    @Transactional
    public void dummy(@Valid MemberInput input) {
    }

    @Cacheable
    public List<Team> retrieveTeam(String name) {
        return teamRepository.findByName(name, PageRequest.of(0, 10));
    }
}
