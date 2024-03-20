package com.kimxavi87.normal.dns;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Flags;
import org.xbill.DNS.Message;
import org.xbill.DNS.Name;
import org.xbill.DNS.RRSIGRecord;
import org.xbill.DNS.RRset;
import org.xbill.DNS.Rcode;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.Section;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.TXTRecord;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;
import org.xbill.DNS.dnssec.ValidatingResolver;
import org.xbill.DNS.utils.base64;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DnsQueryTests {
    private List<Record> query(String domain, int type) throws IOException {
        Record question = Record.newRecord(Name.fromString(domain), type, DClass.IN);
        Message query = Message.newQuery(question);
        Resolver resolver = new SimpleResolver(new InetSocketAddress("localhost", 530));

        Message response = resolver.send(query);
        return response.getSection(Section.ANSWER);
    }

    static String ROOT = ". IN DS 20326 8 2 E06D44B80B8F1D39A95C0B0D7C65D08458E880409BBC683457104237C7F8EC8D";

    @Test
    void validatingSend() throws Exception {
        // Send the same queries using the validating resolver with the
        // trust anchor of the root zone
        // http://data.iana.org/root-anchors/root-anchors.xml

        Resolver sr = new SimpleResolver(new InetSocketAddress("8.8.8.8", 53));
        ValidatingResolver vr = new ValidatingResolver(sr);
        vr.loadTrustAnchors(new ByteArrayInputStream(ROOT.getBytes(StandardCharsets.US_ASCII)));
        System.out.println("\n\nValidating resolver:");
        sendAndPrint(vr, "www.dnssec-failed.org.");
        sendAndPrint(vr, "www.isc.org.");
    }

    private static void sendAndPrint(Resolver vr, String name) throws IOException {
        System.out.println("\n---" + name);
        Record qr = Record.newRecord(Name.fromConstantString(name), Type.A, DClass.IN);
        Message response = vr.send(Message.newQuery(qr));
        System.out.println("AD-Flag: " + response.getHeader().getFlag(Flags.AD));
        System.out.println("RCode:   " + Rcode.string(response.getRcode()));
        for (RRset set : response.getSectionRRsets(Section.ADDITIONAL)) {
            if (set.getName().equals(Name.root) && set.getType() == Type.TXT
                    && set.getDClass() == ValidatingResolver.VALIDATION_REASON_QCLASS) {
                System.out.println("Reason:  " + ((TXTRecord) set.first()).getStrings().get(0));
            }
        }
    }

    @Test
    public void wire() throws TextParseException {
        Record record = Record.newRecord(Name.fromString("."), Type.DNSKEY, DClass.IN);
        byte[] bytes = record.toWire(Section.ANSWER);

        for (int i = 0; i < bytes.length; i++) {
            System.out.println((int) bytes[i]);
        }

        StringBuilder stringBuilder = new StringBuilder();
    }

    @Test
    public void test() throws IOException {
        String domain = "keonwoo.com.";
        List<Record> answers = query(domain, Type.RRSIG);

        System.out.println("RRSIG 레코드:" + answers.toString());
        for (Record answer : answers) {
            if (answer instanceof RRSIGRecord) {
                RRSIGRecord rrsig = (RRSIGRecord) answer;
                System.out.println(rrsig);

                Rrsig rrsigDto = Rrsig.builder()
                        .type(Type.string(rrsig.getRRsetType()))
                        .algorithm(rrsig.getAlgorithm())
                        .labels(rrsig.getLabels())
                        .ttl(rrsig.getOrigTTL())
                        .expiration(rrsig.getExpire().getEpochSecond())
                        .inception(rrsig.getTimeSigned().getEpochSecond())
                        .keyTag(rrsig.getFootprint())
                        .name(rrsig.getSigner().toString())
                        .signature(base64.toString(rrsig.getSignature()))
                        .build();
                System.out.println("DTO: " + rrsigDto);
            }
        }
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Rrsig {
        private String type;
        private Integer algorithm;
        private Integer labels;
        private Long ttl;
        private Long expiration;
        private Long inception;
        private Integer keyTag;
        private String name;
        private String signature;
    }
}
