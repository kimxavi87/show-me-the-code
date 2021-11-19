package com.kimxavi87.spring;

import com.kimxavi87.spring.player.dto.ValueInject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ValueTests {
    @Autowired
    private ValueInject valueInject;

    @Test
    public void givenValueInProperties_whenInjectConstructorValue_thenGetValue() {
        // 없는 properties를 @Value로 생성자 주입 받을 경우 IllegalArgumentException 발생
        // Caused by: java.lang.IllegalArgumentException: Could not resolve placeholder 'no.id' in value "${no.id}"

        // 필드로 주입받은 값을 생성자에서 쓸 수 없음
        System.out.println(valueInject.getId());
        System.out.println(valueInject.getMo());
        assertThat(valueInject.getId()).isEqualTo("100");
        assertThat(valueInject.getMo()).isEqualTo("1001");
    }

}
