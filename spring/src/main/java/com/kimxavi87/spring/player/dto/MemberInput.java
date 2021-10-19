package com.kimxavi87.spring.player.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberInput {
    private String name;
    private int age;
}
