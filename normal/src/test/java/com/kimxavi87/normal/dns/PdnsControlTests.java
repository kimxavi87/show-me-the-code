package com.kimxavi87.normal.dns;

import jnr.unixsocket.UnixSocketAddress;
import jnr.unixsocket.UnixSocketChannel;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;

public class PdnsControlTests {

    @Test
    public void socketWrite() throws IOException {
        String path = "/Users/user/Desktop/workspace/pdns/pdns/_socket/pdns.controlsocket";
        String zoneName = "keonwoo.com.";

        PdnsControlCenter pdnsControlCenter = new PdnsControlCenter(path);
        pdnsControlCenter.ping();
        pdnsControlCenter.notifyZone(zoneName);
        pdnsControlCenter.purge(zoneName);
    }

    public static class PdnsControlCenter {
        private final File socketFile;

        public PdnsControlCenter(String socketPath) {
            socketFile = new File(socketPath);
        }

        public void ping() throws IOException {
            this.sendMessage("RPING");
        }

        public void purge(String zoneName) throws IOException {
            this.sendMessage("PURGE " + zoneName);
        }

        public void notifyZone(String zoneName) throws IOException {
            this.sendMessage("PURGE " + zoneName);
            this.sendMessage("NOTIFY " + zoneName);
        }

        private String sendMessage(String message) throws IOException {
            // Unix 도메인 소켓 주소 생성
            UnixSocketAddress address = new UnixSocketAddress(socketFile);
            // 소켓 채널 생성 및 연결
            UnixSocketChannel channel = UnixSocketChannel.open(address);
            // 출력 스트림 및 입력 스트림 획득
            OutputStream outputStream = Channels.newOutputStream(channel);
            InputStream inputStream = Channels.newInputStream(channel);

            // 서버에 메시지 전송
            outputStream.write((message + "\n").getBytes());
            outputStream.flush();
            System.out.println("Sent: " + message);

            // 서버로부터 응답 수신
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);

            // 소켓 채널 종료
            channel.close();

            String receivedMessage = new String(buffer, 0, bytesRead);
            System.out.println("Received: " + receivedMessage);
            return receivedMessage;
        }
    }
}
