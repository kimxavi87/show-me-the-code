package com.kimxavi87.reactivestreams;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class FluxTests {

    @Test
    public void range() {
        log.info("START");
        Flux.range(0, 10)
                .map(i -> i * 2)
                .doOnNext(integer -> log.info("{}", integer))
                .subscribe();
        log.info("END");
    }

    // Fuseable 있고 없고 차이는 Subscription이 QueueSubscription를 interface 상속 하느냐 마느냐 차이
    // QueueSubscription을 내부 필드로 가지고, sourceMode 를 또 가짐
    // => Queue 는 poll/clear/size/isEmpty 만 internal로 사용한다
    // onNext 다름 - sourceMode 가 ASYNC 이면 onNext(null) 보냄
    // requestFusion

    // conditional vs normal

    @Test
    public void interval() throws InterruptedException {
        Flux.interval(Duration.ofSeconds(1))
                .doOnNext(System.out::println)
                .map(l -> {
                    if (l == 5) {
                        throw new RuntimeException("Excpetion");
                    }
                    return 1;
                })
                .onErrorContinue((e, o) -> System.out.println(e.getMessage() + " : " + o))
                .doOnComplete(() -> System.out.println("success"))
                .doOnCancel(() -> System.out.println("cancel"))
                .doOnError(e -> System.out.println("error"))
                .subscribe(l -> System.out.println("next"),
                        e -> System.out.println(e),
                        () -> System.out.println("Success"));

        Thread.sleep(10 * 1000);
    }
}
