package com.kimxavi87.mock;

import java.net.UnknownHostException;

public class App {
    public static void main(String[] args) throws UnknownHostException {
        MockTcpServer mockTcpServer = new MockTcpServer();
        mockTcpServer.start();
    }
}
