package com.kimxavi87.reactivestreams.customer;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// ReactiveCrudRepository 는 ReactiveMongoRepository 부모로 존재
public interface CustomerReactiveRepository extends ReactiveMongoRepository<Customer, String> {
    Mono<Customer> findByName(String name);
    Flux<Customer> findByBirth(int birth, Pageable pageable);
}
