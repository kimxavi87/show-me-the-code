package com.kimxavi87.spring.player.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@AllArgsConstructor
public class TeamInput {
    private String teamName;
    private int teamValue;

    private List<MemberInput> members;
}
