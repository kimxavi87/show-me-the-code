package com.kimxavi87.spring.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class PropertiesExampleTests {
    @Autowired
    PropertiesExample propertiesExample;

    @Test
    void test() {
        assertThat(propertiesExample.getIdid()).isEqualTo(400);
    }
}
