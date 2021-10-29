package com.kimxavi87.reactivestreams.Customer;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerReactiveRepository extends ReactiveMongoRepository<Customer, String> {
}
