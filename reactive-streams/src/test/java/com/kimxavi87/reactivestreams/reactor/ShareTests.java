package com.kimxavi87.reactivestreams.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class ShareTests {

    @Test
    public void normal() {
        Flux<Integer> shareFlux = Flux.range(0, 10);
//                .subscribeOn(Schedulers.parallel())
//                .doOnNext(integer -> log.info("before : "))
//                .publishOn(Schedulers.parallel())
//                .doOnNext(integer -> log.info("after : "));
//                .share();
        // todo : subscribeOn
        // todo : Schedulers 각각 특징
        // todo : check merge

        Flux<Integer> multiply2 = shareFlux.map(integer -> integer * 2)
                .doOnNext(integer -> log.info("multiply2  : {}", integer));

        Flux<Integer> multiply3 = shareFlux.map(integer -> integer * 3)
                .doOnNext(integer -> log.info("multiply3  : {}", integer));

        Flux.merge(multiply2, multiply3).subscribe();
    }

    @Test
    public void share() {
        // publish 가 cold -> hot 으로 바꿔줌
        // Flux -> ConnectableFlux
        // ConnectableFlux connect() : 구독을 완료한 후에 구독자들에게 값을 보내기 시작함
        // autoConnect, refCount : 구독 개수를 채우면 자동으로 값을 보내기 시작함
        // publish().refCount() => 추상화 한 것이 share()

        // cold vs hot
        // cold 는 구독 할 때마다 타임라인을 생성
        // hot 은 타임라인 하나로만 구독자들에게 전달

        ConnectableFlux<Integer> shareFlux = Flux.range(0, 10).publish();

        shareFlux.map(integer -> integer * 2)
                .doOnNext(integer -> log.info("multiply2  : {}", integer))
                .subscribe();

        shareFlux.map(integer -> integer * 3)
                .doOnNext(integer -> log.info("multiply3  : {}", integer))
                .subscribe();

        shareFlux.connect();
    }

    @Test
    public void publishOn() {
        // todo : publishOn
        Flux<Integer> flux = Flux.range(0, 10)
                .doOnNext(integer -> log.info("before : {}", integer))
                .publishOn(Schedulers.parallel())
                .doOnNext(integer -> log.info("after : {}", integer));

        // before : main
        // after : p1, p2
        // m2 : p1
        // m3 : p2

        flux.map(integer -> integer * 2)
                .doOnNext(integer -> log.info("multiply2  : {}", integer))
                .subscribe();

        flux.map(integer -> integer * 3)
                .doOnNext(integer -> log.info("multiply3  : {}", integer))
                .subscribe();
    }

    @Test
    public void publishOnInSharedFlux() {
        Flux<Integer> shareFlux = Flux.range(0, 10).share();

        shareFlux
                .publishOn(Schedulers.newSingle("multiply2"))
                .map(integer -> integer * 2)
                .doOnNext(integer -> log.info("multiply2  : {}", integer))
                .subscribe();

        shareFlux
                .publishOn(Schedulers.newSingle("multiply3"))
                .map(integer -> integer * 2)
                .map(integer -> integer * 3)
                .doOnNext(integer -> log.info("multiply3  : {}", integer))
                .subscribe();

        // main thread
        shareFlux
                .doOnNext(integer -> log.info("main : {}", integer))
                .subscribe();

        // main thread2
        shareFlux
                .doOnNext(integer -> log.info("main2 : {}", integer))
                .subscribe();

        // main -> main2 -> main end

        // 위 main thread로 작동하는 부분을 제거하면 main end로 바로 호출된다
        log.info("main end");
    }
}
