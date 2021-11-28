package com.kimxavi87.normal.concurrency;

import org.junit.jupiter.api.Test;

public class YieldTests {

    @Test
    public void yield() {
        // JVM의 다양한 쓰레드는 선점형, 우선 순위 기반 스케쥴러를 구현
        // 특정 우선 순위가 존재
        // 선점형, 즉 우선 순위가 높은 쓰레드가 오면, 낮은 쓰레드를 중단(선점) 한다
        // 그러나, 운영체제가 우선 순위가 낮은 쓰레드를 선택할 수도 있다.
        // 자바는 시간 분할적으로 하지 않지만, 보통의 운영체제들이 그렇게 운영한다
        // 우선 순위는 1 ~ 10 의 값을 가지고, 기본값은 5
        // 쓰레드가 시작하기 전에 우선 순위를 지정해야

        // yield 메서드는 다른 쓰레드가 선점하도록 할 용의가 있음을 JVM에 알림
        // 힌트일 뿐이며, 어떤 효과도 보장해주진 않는다
        // running 상태에서 runnable 상태로만 만들 수 있음

        Thread min = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++) {
                    System.out.println("min: " + i);
                    Thread.yield();
                }
            }
        });

        Thread max = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++) {
                    System.out.println("max: " + i);
                    Thread.yield();
                }
            }
        });

        min.setPriority(Thread.MIN_PRIORITY);
        max.setPriority(Thread.MAX_PRIORITY);

        max.start();
        min.start();
        // 별로 효과가.....

    }

    @Test
    public void join() {
        // join()
        // 다른 쓰레드가 끝날 때까지 쓰레드가 실행하지 않도록
        // 쓰레드 실행을 다른 쓰레드의 끝에 결합할 수 있도록 사용
        // 현재 실행중인 쓰레드는 다른 쓰레드가 끝날 때까지 Blocking된다.

        Thread join = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Start");
                System.out.println("Sleep");

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("End");
            }
        });

        join.start();
        try {
            join.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread second = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Second Start");
                System.out.println("Second End");
            }
        });

        // 위에 join이 끝날 때까지 기다린다
        second.start();
    }
}
