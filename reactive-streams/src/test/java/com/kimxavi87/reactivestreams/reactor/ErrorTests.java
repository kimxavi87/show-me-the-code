package com.kimxavi87.reactivestreams.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class ErrorTests {

    @Test
    public void noOptions() {
        // exception 이 발생해버리는 경우 0 만 나오고, 그 다음 스트림은 처리되지 않음 끝남
        // ERROR reactor.core.publisher.Operators - Operator called default onErrorDropped
        // reactor.core.Exceptions$ErrorCallbackNotImplemented 발생
        Flux.range(0, 10)
                .map(i -> i * 2)
                .doOnNext(integer -> log.info("{}", integer))
                .map(i -> i / 0)
                .subscribe();
    }

    @Test
    public void onErrorContinue() {
        // onErrorContinue -> 스트림 끝나지 않음
        Flux.range(0, 10)
                .map(i -> i * 2)
                .doOnNext(integer -> log.info("{}", integer))
                .map(i -> i / 0)
                .onErrorContinue((throwable, i) -> {
                    log.error("error", throwable);
                })
                .subscribe();
    }

    @Test
    public void onErrorResume() {
        // onErrorResume -> 스트림 끝남
        Flux.range(0, 10)
                .map(i -> i * 2)
                .doOnNext(integer -> log.info("{}", integer))
                .map(i -> i / 0)
                .onErrorResume(throwable -> {
                    log.error("error", throwable);
                    //return Mono.empty();
                    return Mono.just(10);
                })
                .subscribe();
    }
}
