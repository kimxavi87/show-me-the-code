package com.kimxavi87.reactivestreams.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class ShareTests {

    @Test
    public void gwt() {
        Flux<Integer> shareFlux = Flux.range(0, 10);
//                .subscribeOn(Schedulers.parallel())
//                .doOnNext(integer -> log.info("before : "))
//                .publishOn(Schedulers.parallel())
//                .doOnNext(integer -> log.info("after : "));
//                .share();
        // todo : share 동작 원리?
        // todo : subscribeOn
        // todo : publishOn
        // todo : Schedulers 각각 특징

        Flux<Integer> multiply2 = shareFlux.map(integer -> integer * 2)
                .doOnNext(integer -> log.info("multiply2  : {}", integer));

        Flux<Integer> multiply3 = shareFlux.map(integer -> integer * 3)
                .doOnNext(integer -> log.info("multiply3  : {}", integer));

        Flux.merge(multiply2, multiply3).subscribe();
    }
}
