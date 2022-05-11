package com.kimxavi87.normal.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class LockTests {

    @Test
    public void lock() throws InterruptedException {
        TotalCounter totalCounter = new TotalCounter();

        // 쓰레드가 락을 획득한 경우 synchronized 해당 객체에 대해 다른 영역에 재진입이 가능하다
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("RUN1");
                    totalCounter.plusplus();
                    totalCounter.plusplusSync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("RUN2");
                    totalCounter.plusplus();
                    totalCounter.plusplusSync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(thread1.isDaemon());
        System.out.println(thread2.isDaemon());

//        thread1.setDaemon(true);
//        thread2.setDaemon(true);

        System.out.println(thread1.isDaemon());
        System.out.println(thread2.isDaemon());

        thread1.start();
        thread2.start();
        // synchronized 는 대기중인 쓰레드 간의 진입 순서를 보장해주지 않는다

        Thread.sleep(10 * 1000);
    }

    @Test
    public void semaphore() {

    }

    public static class TotalCounter {
        private int count = 0;

        public void plusplus() throws InterruptedException {
            synchronized (this) {
                System.out.println("plusplus");
                Thread.sleep(3000);
                count++;
            }
        }

        public synchronized void plusplusSync() throws InterruptedException {
            System.out.println("plusplusSync");
            Thread.sleep(3000);
            count++;
        }

    }
}
