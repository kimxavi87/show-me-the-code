package com.kimxavi87.normal.collections;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueTests {

    @Test
    public void blockingQueue() {
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();

        // add vs offer
        // add 는 큐의 제한에 걸리면 IllegalStateException 를 낸다
        // offer 는 false 를 반환한다
        // AbstractQueue.add() 코드를 보면 offer를 호출하고 false이면 Exception 던지도록 되어있음
        // offer 내부적으로 ReentrantLock 을 사용
        blockingQueue.add("Hello Queue!");
        blockingQueue.offer("Hello Queue offer!");
    }
}
