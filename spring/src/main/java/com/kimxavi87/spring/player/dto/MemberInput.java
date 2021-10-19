package com.kimxavi87.spring.player.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;

@Builder
@Getter
@AllArgsConstructor
public class MemberInput {
    private String name;
    @Max(value = 30, message = "sorry, too old")
    private int age;
}
