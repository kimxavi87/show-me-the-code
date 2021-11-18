package com.kimxavi87.spring.player.dto;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ValueInject {
    private String id;

    public ValueInject(@Value("${my.id}") String id) {
        this.id = id;
    }
}
