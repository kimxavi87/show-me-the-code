package com.kimxavi87.spring;

import com.kimxavi87.spring.conf.InjectProperties;
import com.kimxavi87.spring.conf.MyProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PropertyTests {
    @Resource
    private MyProperties myProperties;

    @Autowired
    private InjectProperties injectProperties;

    @Test
    public void givenValueInAppYml_whenInjectResource_thenGet() {
        assertThat(myProperties.getId()).isEqualTo(100);
    }

    @Test
    public void givenValueInAppYml_whenInjectConstructor_thenGet() {
        assertThat(injectProperties.getId()).isEqualTo(100);
    }
}
