package com.kimxavi87.mock;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MockTcpServer implements Closeable {
    private InetSocketAddress inetSocketAddress;

    public void start(int port) throws IOException, ExecutionException, InterruptedException, TimeoutException {
        AsynchronousServerSocketChannel serverChannel
                = AsynchronousServerSocketChannel.open();

        serverChannel.bind(new InetSocketAddress("127.0.0.1", 4555));
        // server.bind(null);

        Future<AsynchronousSocketChannel> acceptFuture = serverChannel.accept();
        AsynchronousSocketChannel clientChannel = acceptFuture.get(10, TimeUnit.SECONDS);
        if ((clientChannel != null) && (clientChannel.isOpen())) {
            while (true) {
                ByteBuffer buffer = ByteBuffer.allocate(32);
                Future<Integer> readResult  = clientChannel.read(buffer);

                // perform other computations
                readResult.get();

                buffer.flip();
                String message = new String(buffer.array()).trim();
                if (message.equals("bye")) {
                    break; // while loop
                }
                buffer = ByteBuffer.wrap(message.getBytes());
                Future<Integer> writeResult = clientChannel.write(buffer);

                // perform other computations

                writeResult.get();
                buffer.clear();
            }

            clientChannel.close();
            serverChannel.close();
        }

    }


    @Override
    public void close() throws IOException {
    }
}
