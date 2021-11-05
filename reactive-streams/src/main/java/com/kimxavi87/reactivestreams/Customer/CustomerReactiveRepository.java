package com.kimxavi87.reactivestreams.Customer;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

// ReactiveCrudRepository 는 ReactiveMongoRepository 부모로 존재
public interface CustomerReactiveRepository extends ReactiveMongoRepository<Customer, String> {
    Mono<Customer> findByName(String name);
}
