package com.kimxavi87.spring;

import com.kimxavi87.spring.player.entity.Contest;
import org.junit.jupiter.api.Test;

public class LombokBuilderTests {
    @Test
    public void builderTest() {
        Contest.builder()
                .days(5)
                .name("Builder parameter just 2")
                .build();
    }
}
