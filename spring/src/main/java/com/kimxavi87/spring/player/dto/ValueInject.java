package com.kimxavi87.spring.player.dto;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ValueInject {

    private final String id;
    private final String mo = "1001";

    // 필드 @Value injection 도 없으면 에러 발생
//    @Value("${no.id}")
//    private String no;

    public ValueInject(@Value("${my.id}") String id) {
        this.id = id;
    }
}
