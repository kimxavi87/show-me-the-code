package com.kimxavi87.spring;

import com.kimxavi87.spring.conf.MyProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PropertyTests {
    @Resource
    private MyProperties myProperties;

    @Test
    public void givenValueInAppYml_whenInjectResource_thenGet() {
        assertThat(myProperties.getId()).isEqualTo(100);
    }
}
