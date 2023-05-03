package com.kimxavi87.normal.dns;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.junit.jupiter.api.Test;
import org.xbill.DNS.utils.base64;

import java.io.IOException;
import java.io.StringWriter;
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
public class DnssecTests {

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
}
