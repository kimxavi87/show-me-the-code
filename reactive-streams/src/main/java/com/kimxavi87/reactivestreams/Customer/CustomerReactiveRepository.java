package com.kimxavi87.reactivestreams.Customer;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

// ReactiveCrudRepository 는 ReactiveMongoRepository 부모로 존재
public interface CustomerReactiveRepository extends ReactiveMongoRepository<Customer, String> {
}
