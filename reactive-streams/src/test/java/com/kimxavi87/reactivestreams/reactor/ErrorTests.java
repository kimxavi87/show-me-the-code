package com.kimxavi87.reactivestreams.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class ErrorTests {

    @Test
    public void nothing() {
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
    public void errorConsumer() {
        // error consumer 가 있는 경우에도 handling 만 될 뿐 스트림은 멈춘다
        Flux.range(0, 10)
                .map(i -> i * 2)
                .doOnNext(integer -> log.info("{}", integer))
                .map(i -> i / 0)
                .subscribe(integer -> {}, throwable -> {
                    log.error("error", throwable);
                });
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
    public void onErrorContinueWith() {
        Flux.range(0, 10)
                .map(i -> i * 2)
                .doOnNext(integer -> log.info("before {}", integer))
                .map(i -> i / 0)
                .doOnNext(integer -> log.info("before-e {}", integer))
                .doOnError(integer -> log.info("before-e"))
                .log()
                .onErrorContinue((throwable, i) -> {
                    log.error("error", throwable);
                })
                .onErrorReturn(1000)
                .doOnNext(integer -> log.info("after-n {}", integer))
                .doOnError(integer -> log.info("after-e"))
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

    @Test
    public void onErrorResumeWithOnErrorContinue() {
        // onErrorResume -> 스트림이 error로 계속 전달됨
        // onErrorContinue -> continue; 문 처럼 loop 의 위로 올라가는 것
        Flux.range(0, 10)
                .map(i -> i * 2)
                .doOnNext(integer -> log.info("{}", integer))
                .map(i -> i / 0)
                .onErrorResume(throwable -> {
                    log.error("error", throwable);
                    //return Mono.empty();
                    return Mono.just(10);
                })
                .onErrorContinue((throwable, o) -> log.info("continue {}", o))
                .subscribe();
    }
}
