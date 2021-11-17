package com.kimxavi87.normal.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class RunnableCallableTests {

    @Test
    public void runnable() {
        // Return 없음. Exception 전달도 안 됨
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 20);
                    System.out.println("Hello World!");
                } catch (Exception e) {
                    System.out.println("err");
                }
            }
        };

        // runnable.run();
        Thread thread = new Thread(runnable);
        thread.start();
        System.out.println("End");
    }

    @Test
    public void callabele() throws ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<>() {
            @Override
            public String call() throws Exception {
                System.out.println("Hello Callable!");
                return "Hello World!";
            }
        };
        FutureTask futureTask = new FutureTask(callable);
        Thread thread = new Thread(futureTask);
        thread.start();

        System.out.println(futureTask.get());
    }

    @Test
    public void executorService() throws ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<>() {
            @Override
            public String call() throws Exception {
                System.out.println("Hello Callable!");
                return "Hello World!";
            }
        };
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(callable);

        System.out.println(future.get());
    }
}
