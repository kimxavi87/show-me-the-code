package com.kimxavi87.spring;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class ApplicationTests {

    @Test
    public void loadContext() {
        log.info("LOAD CONTEXT");
    }
}
