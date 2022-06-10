package com.kimxavi87.reactivestreams.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.Callable;

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

    @Disabled
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

    @Test
    public void flatMap() {
        Flux.range(0, 10)
                // main thread
                .doOnNext(integer -> log.info("before {}", integer))
                // newSingle
                .flatMap(integer ->
                        Mono.fromCallable(() -> {
                            log.info("from callable " + integer);
                            try {
                                Thread.sleep(2000L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return "Hello World " + integer;
                        }).publishOn(Schedulers.newSingle("defer"))
                )
                // newSingle
                .doOnNext(s -> log.info(s))
                .blockLast();
    }

    @Test
    public void monoCannotReturnNull() {
        // mono<?> 을 return null 로 하면 error 발생한다
    }

    @Test
    public void using() {
        // callable 처리로 받아서
        // publisher 를 만든 다음에
        // doOnNext 를 처리하고
        // clean up 호출됨

        // TODO eager true, false?
        Flux.using(() -> "abc",
                        Mono::just,
                        s -> log.info("clean up {}", s),
                        false)
                .doOnNext(s -> log.info("doOnNext {}", s))
                .subscribe();
    }

    @Test
    public void usingWhen() {
        // publisher source 를 뽑아내는 publisher를 만듦
        // source 로 부터 resource 를 뽑아냄

        // ex
        // file open -> stream 만들어내서 전달
        // stream 으로부터 file read 해서 뽑아냄
        // async clean up
    }
}
