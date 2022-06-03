package com.kimxavi87.normal.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class GuavaSimpleCacheTests {

    @Test
    public void simple() throws InterruptedException {
        Cache<Long, String> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();

        cache.put(1L, "Park");

        Thread.sleep(5 * 1000);
        System.out.println(cache.getIfPresent(1L));
        System.out.println(cache.size());

        Thread.sleep(5 * 1000);

        System.out.println(cache.getIfPresent(1L));
        System.out.println(cache.size());
    }
}
