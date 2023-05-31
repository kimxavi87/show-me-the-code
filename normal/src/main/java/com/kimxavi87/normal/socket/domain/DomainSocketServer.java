package com.kimxavi87.normal.socket.domain;

import jnr.unixsocket.UnixServerSocket;
import jnr.unixsocket.UnixServerSocketChannel;
import jnr.unixsocket.UnixSocketAddress;
import jnr.unixsocket.UnixSocketChannel;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DomainSocketServer implements Runnable {
    private static final String SOCKET_PATH = "_tmp.socket";
    private final UnixServerSocketChannel unixServerSocketChannel;
    private final ExecutorService threadPool;
    private final String socketPath;

    public DomainSocketServer() throws IOException {
        UnixServerSocketChannel unixServerSocketChannel = UnixServerSocketChannel.open();
        UnixServerSocket socket = unixServerSocketChannel.socket();
        socket.bind(new UnixSocketAddress(SOCKET_PATH));

        this.unixServerSocketChannel = unixServerSocketChannel;
        this.threadPool = Executors.newSingleThreadExecutor();
        UnixSocketAddress localSocketAddress = unixServerSocketChannel.getLocalSocketAddress();
        this.socketPath = localSocketAddress.path();
    }

    public String getSocketPath() {
        return socketPath;
    }

    public void start() {
        threadPool.submit(this);
    }

    @Override
    public void run() {
        try {
            while (unixServerSocketChannel.isOpen()) {
                try (UnixSocketChannel clientSocketChannel = unixServerSocketChannel.accept()) {
                    InputStream inputStream = Channels.newInputStream(clientSocketChannel);
                    byte[] buffer = new byte[1024];
                    int bytesRead = inputStream.read(buffer);

                    String received = new String(buffer, 0, bytesRead).replace("\n", "");
                    System.out.println("SERVER RECEIVED: " + received);

                    String message = "RESPONSE";
                    System.out.println("SERVER SENT: " + message);

                    ByteBuffer writeBuffer = ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));
                    clientSocketChannel.write(writeBuffer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() throws IOException {
        threadPool.shutdown();
        unixServerSocketChannel.close();
        Files.deleteIfExists(Path.of(this.socketPath));
    }
}
