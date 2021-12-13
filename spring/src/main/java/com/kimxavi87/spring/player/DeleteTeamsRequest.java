package com.kimxavi87.spring.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class DeleteTeamsRequest {
    private Set<String> names;
}
