package com.kimxavi87.spring;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Slf4j
public class Junit5Tests {

    @BeforeEach
    public void beforeEach() {
        log.info("beforeEach");
    }

    @BeforeAll
    public static void beforeAll() {
        log.info("static beforeAll");
    }

    @DisplayName("테스트 설명을 넣는다")
    @Test
    public void test() {
        log.info("test");
    }

    @Disabled("비활성화 됨")
    @Test
    public void disabled() {
        log.info("disabled");
    }

    @Test
    public void testAssumeTrue() {
        int a = 10;
        int b = 5;

        assumeTrue(a < b);
        // assumeTrue 를 통과하지 못하면 ignore 되어버림

        log.info("assume true");
    }

    @Test
    public void testAssertThrows() {
        // Assertions
        // 예외 클래스를 넣어주고 로직을 넣어줌
        // Exception 반환
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {

        });
    }
}
