package com.kimxavi87.normal.dns;

import jnr.unixsocket.UnixSocketAddress;
import jnr.unixsocket.UnixSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.junit.jupiter.api.Test;
import org.xbill.DNS.utils.base64;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.channels.Channels;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.RSAPrivateCrtKey;

@Slf4j
public class DnsTests {

    @Test
    public void rsa() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
            keyPairGenerator.initialize(2048, new SecureRandom());

            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            StringWriter publicKeyWriter = new StringWriter();
            JcaPEMWriter pemWriter = new JcaPEMWriter(publicKeyWriter);
            pemWriter.writeObject(publicKey);
            pemWriter.close();

            StringWriter privateKeyWriter = new StringWriter();
            pemWriter = new JcaPEMWriter(privateKeyWriter);
            pemWriter.writeObject(privateKey);
            pemWriter.close();

            System.out.println("RSA Public Key:\n" + publicKeyWriter.toString());
            System.out.println("RSA Private Key:\n" + privateKeyWriter.toString());
        } catch (NoSuchAlgorithmException | NoSuchProviderException | IOException e) {
            System.err.println("Error generating RSA key pair: " + e.getMessage());
        }
    }

    @Test
    public void rsaContent() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
            keyPairGenerator.initialize(2048, new SecureRandom());
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            RSAPrivateCrtKey privateKey = (RSAPrivateCrtKey) keyPair.getPrivate();

            String modulus = base64.toString(privateKey.getModulus().toByteArray());
            String publicExponent = base64.toString(privateKey.getPublicExponent().toByteArray());
            String privateExponent = base64.toString(privateKey.getPrimeExponentP().toByteArray());
            String prime1 = base64.toString(privateKey.getPrimeP().toByteArray());
            String prime2 = base64.toString(privateKey.getPrimeQ().toByteArray());
            String exponent1 = base64.toString(privateKey.getPrimeExponentP().toByteArray());
            String exponent2 = base64.toString(privateKey.getPrimeExponentQ().toByteArray());
            String coefficient = base64.toString(privateKey.getCrtCoefficient().toByteArray());

            String content = "Private-key-format: v1.2\n"
                    + "Algorithm: 8 (RSASHA256)\n"
                    + "Modulus: " + modulus + "\n"
                    + "PublicExponent: " + publicExponent + "\n"
                    + "PrivateExponent: " + privateExponent + "\n"
                    + "Prime1: " + prime1 + "\n"
                    + "Prime2: " + prime2 + "\n"
                    + "Exponent1: " + exponent1 + "\n"
                    + "Exponent2: " + exponent2 + "\n"
                    + "Coefficient: " + coefficient;
            System.out.println(content);

        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error generating RSA key pair: " + e.getMessage());
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }

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
