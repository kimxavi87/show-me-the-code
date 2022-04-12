package com.kimxavi87.spring.cycle;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class CircularTests {
    @Autowired
    CycleService3 cycleService3;

    @Autowired
    CycleService4 cycleService4;

    @Test
    public void test() {
        cycleService3.service();
        cycleService4.service();
    }
}
