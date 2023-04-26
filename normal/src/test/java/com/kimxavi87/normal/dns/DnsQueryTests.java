package com.kimxavi87.normal.dns;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Message;
import org.xbill.DNS.Name;
import org.xbill.DNS.RRSIGRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.Section;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.Type;
import org.xbill.DNS.utils.base64;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

public class DnsQueryTests {
    private List<Record> query(String domain, int type) throws IOException {
        Record question = Record.newRecord(Name.fromString(domain), type, DClass.IN);
        Message query = Message.newQuery(question);
        Resolver resolver = new SimpleResolver(new InetSocketAddress("localhost", 530));

        Message response = resolver.send(query);
        return response.getSection(Section.ANSWER);
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