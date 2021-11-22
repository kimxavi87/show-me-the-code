package com.kimxavi87.reactivestreams.conf;

import reactor.core.Disposable;

import java.util.LinkedList;
import java.util.Queue;

public enum ShutdownQueue {
    INSTANCE;

    static {
        System.out.println("ShutdownQueue : addShutdownHook");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            while (!INSTANCE.disposableList.isEmpty()) {
                System.out.println("Shutdown disposable...");
                Disposable disposable = INSTANCE.disposableList.poll();
                disposable.dispose();
            }
        }));
    }

    private final Queue<Disposable> disposableList = new LinkedList<>();

    public static void addDisposable(Disposable disposable) {
        INSTANCE.disposableList.add(disposable);
    }
}