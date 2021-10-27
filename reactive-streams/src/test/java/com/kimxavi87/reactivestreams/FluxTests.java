package com.kimxavi87.reactivestreams;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class FluxTests {

    @Test
    public void interval() {
        Flux.interval(Duration.ofSeconds(1))
                .doOnNext(System.out::println)
                .blockFirst();
    }
}
