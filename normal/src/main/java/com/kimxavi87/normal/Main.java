package com.kimxavi87.normal;

public class Main {
    public static void main(String[] args) {
        // daemon 쓰레드는 프로그램 본질에 포함되지 않고, daemon이 아닌 쓰레드들이 모두 종료되면 같이 종료된다
        // 반대로 daemon 이 아닌 쓰레드가 실행중이면 프로그램이 종료되지 않는다
        // thread 기본 값은 daemon : false

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

        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
        System.out.println("End");

        Runnable shutdownHook = new Runnable() {
            @Override
            public void run() {
                System.out.println("ShutdownHook");
            }
        };

        Runtime.getRuntime().addShutdownHook(new Thread(shutdownHook));
    }
}
