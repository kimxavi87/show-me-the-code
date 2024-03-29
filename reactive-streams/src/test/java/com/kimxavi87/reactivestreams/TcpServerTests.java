package com.kimxavi87.reactivestreams;

import io.netty.channel.ChannelOption;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.SocketUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class TcpServerTests {
    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    private int heartbeatServerPort;
    private HeartbeatServer heartbeatServer;
    private Future<?> heartbeatServerFuture;

    // jupiter @Test 로 등록 안 하니까 실행 안 되는 단순한 이유
    @BeforeEach
    public void setUp() throws IOException, InterruptedException {
        // findAvailableTcpPort 는 port를 어떻게 찾는 걸까?
        // random int 값 구한 다음에
        // 해당 int 값으로 소켓 열어봄, 계속 반복해서 해보고 열리면 닫고 해당 포트를 반환
        heartbeatServerPort = SocketUtils.findAvailableTcpPort();
        heartbeatServer = new HeartbeatServer(heartbeatServerPort);
        heartbeatServerFuture = threadPool.submit(heartbeatServer);
        if (!heartbeatServer.await(10, TimeUnit.SECONDS)) {
            throw new IOException("fail to start test server");
        }
    }

    @AfterEach
    public void tearDown() throws Exception {
        heartbeatServer.close();
        assertThat(heartbeatServerFuture.get()).isNull();

        threadPool.shutdown();
        threadPool.awaitTermination(5, TimeUnit.SECONDS);
        Thread.sleep(500);
    }

    @Test
    public void whenConnectTcpServer_thenConnectionSuccess() {
        Mono<? extends Connection> connectionMono = TcpClient.create()
                .host("localhost")
                .port(heartbeatServerPort)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .doOnConnect(tcpClientConfig -> System.out.println("doOnConnect"))
                .doOnConnected(connection -> System.out.println("doOnConnected"))
                .doOnDisconnected(connection -> System.out.println("doOnDisconnected"))
                .connect()
                .doOnNext(connection -> System.out.println("doOnNext"));

        // doOnConnect : connect() 에서 호출
        // doOnConnected : 연결이 되고 IO handle 할 때

        StepVerifier.create(connectionMono)
                .assertNext(connection -> {
                    assertThat(connection.isDisposed())
                            .isEqualTo(false);
                    // doOnDisconnected : 연결 끊을 때
                    connection.dispose();
                })
                .expectComplete()
                .verify(Duration.ofMinutes(1));
    }

    @Test
    public void whenConnectAnyTcpPort_thenConnectionFailed() {
        int anyPort = SocketUtils.findAvailableTcpPort();

        Mono<? extends Connection> connectionMono = TcpClient.create()
                .host("localhost")
                .port(anyPort)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .connect();

        StepVerifier.create(connectionMono)
                .expectError()
                .verify(Duration.ofMinutes(1));

    }

    @Test
    public void whenSendStringToOut_thenReceive() {
        // runOn : LoopResources 변경
        // wiretap : Wire Logger
        // todo : neverComplete() ?
        Mono<? extends Connection> connectionMono = TcpClient.create()
                .host("localhost")
                .port(heartbeatServerPort)
                .wiretap(true)
                .handle((in, out) -> {
                    return Flux.merge(out.sendString(Mono.just("Hello World!")).neverComplete(),
                            in.receive().then());
                })
                .connect();

        StepVerifier.create(connectionMono)
                .assertNext(connection -> {
                    assertThat(connection.isDisposed())
                            .isEqualTo(false);
                })
                .expectComplete()
                .verify(Duration.ofMinutes(1));

        Mono<? extends Connection> connectionMono2 = TcpClient.create()
                .host("localhost")
                .port(heartbeatServerPort)
                .wiretap(true)
                .handle((in, out) -> {
                    return Flux.merge(out.sendString(Mono.just("Hello World2!")).neverComplete(),
                            in.receive().then());
                })
                .connect();

        StepVerifier.create(connectionMono2)
                .assertNext(connection -> {
                    assertThat(connection.isDisposed())
                            .isEqualTo(false);
                })
                .expectComplete()
                .verify(Duration.ofMinutes(1));
    }

    private static final class HeartbeatServer extends CountDownLatch implements Runnable {

        final int port;
        private final ServerSocketChannel server;

        private HeartbeatServer(int port) {
            super(1);
            this.port = port;
            try {
                server = ServerSocketChannel.open();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            try {
                server.configureBlocking(true);
                server.socket()
                        .bind(new InetSocketAddress(port));
                countDown();
                while (true) {
                    SocketChannel ch = server.accept();
                    while (server.isOpen()) {
                        ByteBuffer in = ByteBuffer.allocate(32);
                        ch.read(in);
                        System.out.println(new String(in.array(), StandardCharsets.UTF_8));
                        in.flip();

                        ByteBuffer out = ByteBuffer.allocate(1);
                        out.put((byte) '\n');
                        out.flip();
                        ch.write(out);
                    }
                    ch.close();
                }
            }
            catch (Exception e) {
                // Server closed
                System.out.println("CLOSE SERVER");
            }
        }

        public void close() throws IOException {
            ServerSocketChannel server = this.server;
            if (server != null) {
                server.close();
            }
        }
    }
}
