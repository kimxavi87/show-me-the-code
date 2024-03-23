package com.kimxavi87.normal.ignite;

import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.ClientConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IgniteTests {
    static String CACHE_NAME = "myCache";

    @BeforeEach
    void setUp() {
        // Ignite 설정을 생성
        IgniteConfiguration cfg = new IgniteConfiguration();

        // 파티션된 캐시 설정을 생성
        CacheConfiguration cacheCfg = new CacheConfiguration(CACHE_NAME);
        cacheCfg.setCacheMode(CacheMode.PARTITIONED); // 캐시 모드를 파티션으로 설정

        // 캐시 설정을 Ignite 설정에 추가
        cfg.setCacheConfiguration(cacheCfg);

        // Ignite 서버 노드 시작
        Ignition.start(cfg);
    }

    @Test
    void client() {
        // Ignite 서버 노드에 접속하기 위한 ClientConfiguration 인스턴스 생성
        ClientConfiguration cfg = new ClientConfiguration().setAddresses("127.0.0.1:10800");

        // Ignite Thin Client 시작 및 사용
        try (IgniteClient igniteClient = Ignition.startClient(cfg)) {
            // 캐시 접근 (캐시가 존재하지 않으면 생성됩니다)
            ClientCache<Integer, String> cache = igniteClient.getOrCreateCache(CACHE_NAME);

            // 캐시에 데이터 저장
            cache.put(1, "Hello");
            cache.put(2, "World");

            // 캐시에서 데이터 검색 및 출력
            System.out.println("Value for key 1: " + cache.get(1));
            System.out.println("Value for key 2: " + cache.get(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
