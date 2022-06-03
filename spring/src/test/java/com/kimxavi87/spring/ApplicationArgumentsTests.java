package com.kimxavi87.spring;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class ApplicationArgumentsTests {
    @Autowired
    String appString;

    @Test
    public void test() {
        System.out.println(appString);
    }
}
