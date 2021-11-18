package com.kimxavi87.spring;

import com.kimxavi87.spring.player.dto.ValueInject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ValueTests {
    @Autowired
    private ValueInject valueInject;

    @Test
    public void gwt() {
        System.out.println(valueInject.getId());
    }

}
