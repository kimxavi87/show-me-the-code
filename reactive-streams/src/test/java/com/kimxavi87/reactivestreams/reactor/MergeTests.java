package com.kimxavi87.reactivestreams.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;

@Slf4j
public class MergeTests {

    @Test
    public void merge() throws InterruptedException {
        Flux<Integer> integerFlux1 = Flux.fromIterable(Arrays.asList(1, 3, 5, 7, 9))
                .subscribeOn(Schedulers.newSingle("new-1"))
                .doOnNext(i -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
                .doOnNext(i -> log.info("f1 : {}", i));
        Flux<Integer> integerFlux = Flux.fromIterable(Arrays.asList(2, 4, 6, 8, 10))
                .subscribeOn(Schedulers.newSingle("new-2"))
                .doOnNext(i -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
                .doOnNext(i -> log.info("f2 : {}", i));

        // merge 는 각각 동작해서 모임
//        Flux.merge(integerFlux, integerFlux1)
//                .distinct()
//                .doOnNext(i -> log.info("ff : {}", i))
//                .subscribe();

        // concat 은 첫 파라미터로 들어온 flux 실행 되고, 다음 flux 실행되는 동기적 실행
        Flux.concat(integerFlux, integerFlux1)
                .doOnNext(i -> log.info("ff : {}", i))
                .subscribe();

        Thread.sleep(200000);
    }
}
