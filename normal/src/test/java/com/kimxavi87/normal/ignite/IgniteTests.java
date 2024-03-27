package com.kimxavi87.normal.ignite;

import lombok.Value;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.ClientConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.List;

public class IgniteTests {
    static String CACHE_NAME = "myCache";
//    IgniteClient client;
    IgniteCache<Object, Object> client;
    Ignite server;

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
        server = Ignition.start(cfg);
        client = server.cache(CACHE_NAME);

//        ClientConfiguration ccfg = new ClientConfiguration().setAddresses("127.0.0.1:10800");
//        client = Ignition.startClient(ccfg);
    }

    @AfterEach
    void tearDown() {
        server.close();
        client.close();
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

    @Value
    static class Person implements Serializable {
        /** Indexed field. Will be visible to the SQL engine. */
        @QuerySqlField(index = true)
        private long id;

        /** Queryable field. Will be visible to the SQL engine. */
        @QuerySqlField
        private String name;

        @QuerySqlField
        private long timestamp;

//        /** Will NOT be visible to the SQL engine. */
//        private int age;
//
//        /**
//         * Indexed field sorted in descending order. Will be visible to the SQL engine.
//         */
//        @QuerySqlField(index = true, descending = true)
//        private float salary;
    }

    @Test
    void sql() {
        client.query(new SqlFieldsQuery(String.format(
                "CREATE TABLE IF NOT EXISTS Person (id INT PRIMARY KEY, name VARCHAR, timestamp INT) WITH \"VALUE_TYPE=%s\"",
                Person.class.getName())).setSchema("PUBLIC")).getAll();

        for (int i = 0; i < 2000; i++) {
            insertPerson(i, "person " + i);
        }

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 2000; i++) {
            client.query(new SqlFieldsQuery("UPDATE Person SET timestamp = ? WHERE id = ?")
                    .setArgs(i, i).setSchema("PUBLIC")).getAll();
        }

        long endTime = System.currentTimeMillis();

        // 실행 시간 계산 (밀리초 단위)
        long durationInMilliseconds = endTime - startTime;
        System.out.println("time: " + durationInMilliseconds + "ms");

        selectAll();
    }

    void insertPerson(int id, String name) {
        Person val = new Person(id, name, 1000);

        client.query(new SqlFieldsQuery("INSERT INTO Person(id, name, timestamp) VALUES(?, ?, ?)")
                .setArgs(val.getId(), val.getName(), val.getTimestamp())
                .setSchema("PUBLIC")).getAll();
    }

    void selectAll() {
        FieldsQueryCursor<List<?>> cursor = client
                .query(new SqlFieldsQuery("SELECT id, name, timestamp from Person").setSchema("PUBLIC"));

        for (List<?> row : cursor.getAll()) {
            System.out.println("ID: " + row.get(0) + ", Name: " + row.get(1) + ", t: " + row.get(2));
        }
    }
}
