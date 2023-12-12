package com.kimxavi87.reactivestreams.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
public class BufferWIndowTests {

    @Test
    void window() {
        log.info("START");
        Flux.range(0, 10)
                .map(i -> i * 2)
                .doOnNext(integer -> log.info("{}", integer))
                .window(4)
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(integerFlux ->
                        integerFlux.map(integer -> integer * 3)
                                .doOnNext(integer -> log.info("list {}", integer))
                                .subscribe())
                .doOnNext(integer -> log.info("after {}", integer))
                .subscribe();
        log.info("END");
    }

    @Test
    void buffer() {
        log.info("START");
        Flux.range(0, 10)
                .map(i -> i * 2)
                .doOnNext(integer -> log.info("{}", integer))
                .buffer(4)
                .doOnNext(integers -> log.info("list {}", integers))
                .log()
                .subscribe();
        log.info("END");
    }
}
