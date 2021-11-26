package com.kimxavi87.normal.concurrency;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTests {

    @Test
    public void atomic() {

        AtomicInteger atomicInteger = new AtomicInteger();
        // 설정되지 않음
        System.out.println(atomicInteger.compareAndSet(5, 10));
        System.out.println(atomicInteger.get());

        // 설정 됨
        System.out.println(atomicInteger.compareAndSet(0, 10));
        System.out.println(atomicInteger.get());

    }
}
