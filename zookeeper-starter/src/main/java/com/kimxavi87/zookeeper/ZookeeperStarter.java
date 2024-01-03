package com.kimxavi87.zookeeper;

import org.apache.curator.test.TestingServer;

import java.io.IOException;

public class ZookeeperStarter {
    private static TestingServer testingServer;

    static {
        try {
            testingServer = new TestingServer(2181);
        } catch (Exception e) {
            System.out.println("[DBG] ZOOKEEPER FAILED");
        }
    }

    public static void main(String[] args) throws Exception {
        testingServer.start();
        System.out.println("[DBG] ZOOKEEPER START");
        Thread thread = new Thread(() -> {
            try {
                System.out.println("[DBG] ZOOKEEPER END");
                testingServer.close();
            } catch (IOException e) {
                System.out.println("[DBG] ZOOKEEPER END FAILED");
            }
        });

        thread.setDaemon(false);
        Runtime.getRuntime().addShutdownHook(thread);
    }
}
