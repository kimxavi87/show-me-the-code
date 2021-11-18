package com.kimxavi87.reactivestreams.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SchedulersTests {

    @Test
    public void schedulers() {
        Scheduler single = Schedulers.single();

        // parallel
        // default pool size : available processor
        // ParallelScheduler start()
        // ScheduledExecutorService 배열을 pool size 만큼 만들어서
        // 위 array[i]에 ScheduledThreadPoolExecutor 를 생성하고 decorate 하는데
        // Schedulers.enableMetrics() 에서 Decorator 추가
        // 추가되어 있는 Decorator 들로 decorator 해서 반환 (현재는 따로 없는듯 함)
        Scheduler parallel = Schedulers.parallel();

        // boundedElastic
        // max thread : available processor * 10
        // DEFAULT_TTL_SECONDS = 60 milli
        // max queue size : 100000

        // BOUNDED_SERVICES 생성자에서 SHUTDOWN 들어감
        // BOUNDED_SERVICES 새로 생성
        // BoundedServices는 busyQueue 와 idleQueue 를 가짐
        // BoundedServices는 AtomicInteger 상속받아져있음

        // EVICTOR : newScheduledThreadPool 로 thread pool 생성해서 넣음 (core size 1)
        // 그 다음 scheduleAtFixedRate : 작업을 일정 시간 간격으로 반복적으로 실행
        // eviction 메서드를 EVICTOR가 반복실행하게 함
        // idleQueue 에서 BoundedState 중에 위 TTL 보다 많으면 shutdown 해준다

        // BoundedState 는 내부 필드로 ScheduledExecutorService 를 가짐
        Scheduler boundedElastic = Schedulers.boundedElastic();
        // Schedulers.elastic() // deprecated;

        // immediate
        // 우선 싱글턴 객체
        // start()가 아무 구현이 없다
        // publishOn 에 넣어도 그대로 main
        Scheduler immediate = Schedulers.immediate();

        // todo 생성은 위와 같이 알아봤으니 Scheduler를 활용해서 실행하는 것을 확인 필요, 아마도 schedule() 메서드랑 연관있어 보임

        Flux.range(0, 10)
                .publishOn(parallel)
                .map(integer -> integer * 2)
                .doOnNext(integer -> log.info("i : {}", integer))
                .subscribe();
    }
}
