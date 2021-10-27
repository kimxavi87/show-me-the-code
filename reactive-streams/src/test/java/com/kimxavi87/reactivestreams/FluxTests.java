package com.kimxavi87.reactivestreams;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class FluxTests {

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
