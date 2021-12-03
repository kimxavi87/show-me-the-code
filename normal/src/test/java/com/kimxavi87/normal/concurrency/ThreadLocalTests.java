package com.kimxavi87.normal.concurrency;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLocalTests {

    @Test
    public void threadLocal() throws InterruptedException {
        // countDown()을 통해 0이 될때까지 기다림
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Thread thread = new Thread(new Job(countDownLatch));

        thread.setDaemon(false);
        thread.start();
        System.out.println("Thread Name : " + Thread.currentThread().getName());
        System.out.println("thread local : " + Job.threadLocal.get());
        Job.threadLocal.set(10);
        System.out.println("thread local : " + Job.threadLocal.get());

        countDownLatch.await(50, TimeUnit.SECONDS);
    }

    public static class Job implements Runnable {
        // ThreadLocal 내부에서 Map을 통해서 쓰레드별로 값을 제공할 수 있도록 함
        public static ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 5);
        private CountDownLatch countDownLatch;

        public Job(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {

            System.out.println("Thread Name : " + Thread.currentThread().getName());

            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 다른 쓰레드에서 값을 변경했으나, 값이 변경되지 않음
            System.out.println("Sleep end : " + threadLocal.get());
            countDownLatch.countDown();
        }
    }

    @Test
    public void threadLocalWithPool() throws InterruptedException {
        // thread pool 와 함께 사용할 때는 주의 사항이 있다
        // 애플리케이션은 쓰레드 풀에서 특정 작업을 할 때, 쓰레드를 가져온다
        // 다 쓴 쓰레드는 반납한다
        // 또 작업을 하기 위해서 쓰레드를 가져 왔을 때, 이전에 사용하던 ThreadLocal 데이터가
        // 또 사용될 수 있음을 알아야한다

        // 그래서 쓰레드 빌리고, 반납하기 전에 ThreadLocal 을 정리하는 작업이 필요함
        // Executors.newSingleThreadExecutor() 와 유사하게 생성
        ThreadLocalAwareThreadPool threadPool = new ThreadLocalAwareThreadPool(2, 2, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello Runnable");
            }
        });

        Thread.sleep(5000);
    }

    public class ThreadLocalAwareThreadPool extends ThreadPoolExecutor {

        public ThreadLocalAwareThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            System.out.println("before execute");
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            // todo : Call remove on each ThreadLocal
            System.out.println("after execute");
        }
    }
}
