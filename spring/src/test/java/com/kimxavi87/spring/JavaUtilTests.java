package com.kimxavi87.spring;

import org.junit.jupiter.api.Test;

import java.time.Instant;

public class JavaUtilTests {

    @Test
    public void systemTime() {
        System.out.println(System.currentTimeMillis());
        System.out.println(Instant.now().toEpochMilli());
    }
}
