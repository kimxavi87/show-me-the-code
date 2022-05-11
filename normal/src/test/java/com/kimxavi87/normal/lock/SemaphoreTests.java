package com.kimxavi87.normal.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;

public class SemaphoreTests {

    @Test
    public void sem() {
        // 2번째 인자는 FIFO 작동 여부
        Semaphore semaphore = new Semaphore(5, true);
        try {
            semaphore.acquire();
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
