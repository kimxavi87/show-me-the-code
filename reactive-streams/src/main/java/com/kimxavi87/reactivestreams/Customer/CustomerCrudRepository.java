package com.kimxavi87.reactivestreams.customer;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CustomerCrudRepository extends ReactiveCrudRepository<Customer, String> {
    Mono<Customer> findByName(String name);
}
