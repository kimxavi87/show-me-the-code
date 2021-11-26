package com.kimxavi87.mock.server;

import com.kimxavi87.mock.util.NamedRunnable;
import com.kimxavi87.mock.util.Util;

import javax.net.ServerSocketFactory;
import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.kimxavi87.mock.util.Util.closeQuietly;

public class MockTcpServer implements Server, Closeable {
    private static final Logger logger = Logger.getLogger(MockTcpServer.class.getName());

    private int port;
    private InetSocketAddress inetSocketAddress;
    private ExecutorService executor;
    private ServerSocketFactory serverSocketFactory;
    private ServerSocket serverSocket;
    private final Set<Socket> openClientSockets =
            Collections.newSetFromMap(new ConcurrentHashMap<>());

    public MockTcpServer(int port) {
        this.port = port;
    }

    @Override
    public void start() throws Exception {
        inetSocketAddress = new InetSocketAddress(InetAddress.getByName("localhost"), port);
        executor = Executors.newCachedThreadPool(Util.threadFactory("MockWebServer", false));
        if (serverSocketFactory == null) {
            serverSocketFactory = ServerSocketFactory.getDefault();
        }
        serverSocket = serverSocketFactory.createServerSocket();

        // Reuse if the user specified a port
        serverSocket.setReuseAddress(inetSocketAddress.getPort() != 0);
        serverSocket.bind(inetSocketAddress, 50);
        executor.execute(new NamedRunnable("MockWebServer %s", port) {
            @Override protected void execute() {
                try {
                    logger.info(MockTcpServer.this + " starting to accept connections");
                    acceptConnections();
                } catch (Throwable e) {
                    logger.log(Level.WARNING, MockTcpServer.this + " failed unexpectedly", e);
                }

                // Release all sockets and all threads, even if any close fails.
                closeQuietly(serverSocket);
                for (Iterator<Socket> s = openClientSockets.iterator(); s.hasNext(); ) {
                    closeQuietly(s.next());
                    s.remove();
                }

                executor.shutdown();
            }

            private void acceptConnections() throws Exception {
                while (true) {
                    Socket socket;
                    try {
                        socket = serverSocket.accept();
                    } catch (SocketException e) {
                        logger.info(" done accepting connections: " + e.getMessage());
                        return;
                    }

                    openClientSockets.add(socket);
                    serveConnection(socket);
                }
            }
        });
    }

    private void serveConnection(final Socket socket) {
        executor.execute(new NamedRunnable("MockTcpServer %s", socket.getRemoteSocketAddress()) {
            @Override
            protected void execute() {
                try {
                    processConnection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void processConnection() throws IOException {
                // todo socket policy 들에 따라서 처리
                // todo while(processOneRequest)

                socket.close();
                openClientSockets.remove(socket);
            }
        });

    }

    @Override
    public void shutdown() {
        // todo socket close
        // todo thread close
    }

    @Override
    public void close() throws IOException {
        shutdown();
    }


}
