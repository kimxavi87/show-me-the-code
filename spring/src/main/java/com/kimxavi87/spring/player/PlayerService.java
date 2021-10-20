package com.kimxavi87.spring.player;

import com.kimxavi87.spring.player.dto.MemberInput;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
@Service
public class PlayerService {

    public void dummy(@Valid MemberInput input) {
    }
}
