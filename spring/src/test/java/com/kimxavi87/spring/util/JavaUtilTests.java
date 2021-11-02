package com.kimxavi87.spring.util;

import org.junit.jupiter.api.Test;

import java.time.Instant;

public class JavaUtilTests {

    @Test
    public void systemTime() {
        System.out.println(System.currentTimeMillis());
        System.out.println(Instant.now().toEpochMilli());
    }
}
