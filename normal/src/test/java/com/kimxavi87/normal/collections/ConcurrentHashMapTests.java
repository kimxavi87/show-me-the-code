package com.kimxavi87.normal.collections;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTests {

    @Test
    public void cchashMap() {
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
        concurrentHashMap.put("key", "value");
        concurrentHashMap.get("key");

        concurrentHashMap.computeIfAbsent("key", (key) -> "value");
    }
}
