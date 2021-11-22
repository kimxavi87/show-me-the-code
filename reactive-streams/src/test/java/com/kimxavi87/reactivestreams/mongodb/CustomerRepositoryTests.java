package com.kimxavi87.reactivestreams.mongodb;

import com.kimxavi87.reactivestreams.Customer.Customer;
import com.kimxavi87.reactivestreams.Customer.CustomerCrudRepository;
import com.kimxavi87.reactivestreams.Customer.CustomerReactiveRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CustomerRepositoryTests {
    @Autowired
    private CustomerCrudRepository crudRepository;
    @Autowired
    private CustomerReactiveRepository reactiveRepository;
    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    @Test
    public void givenCustomer_whenSaveAndFindByName_thenSuccess() {
        Customer park = crudRepository.save(new Customer(null, "park", 19990909)).block();
        StepVerifier.create(crudRepository.findByName("park"))
                .assertNext(customer -> {
                    System.out.println(customer.getId());
                    assertThat(customer).isEqualTo(park);
                })
                .expectComplete()
                .verify(Duration.ofSeconds(30));
    }

    @Test
    public void givenCustomer_whenReactiveSaveAndFindByName_thenSuccess() {
        // 테스트 동시에 할 때, 데이터가 초기화되는 것은 아니니 조심할 것
        // Equals 를 구현을 안해주면 아래에 isEqualTo 가 같게 나오지 않는다
        Customer park = reactiveRepository.save(new Customer(null, "kim", 19990909)).block();
        StepVerifier.create(reactiveRepository.findByName("kim"))
                .assertNext(customer -> {
                    System.out.println(customer.getId());
                    assertThat(customer).isEqualTo(park);
                })
                .expectComplete()
                .verify(Duration.ofSeconds(30));
    }

    @Test
    public void givenBasicQueryLt_whenQuery_thenFoundSuccess() {
        reactiveRepository.save(new Customer(null, "kim", 10)).block();

        BasicQuery basicQuery = new BasicQuery("{ birth: { $lt: 20 } }");

        Flux<Customer> customerFlux = mongoTemplate.find(basicQuery, Customer.class);

        StepVerifier.create(customerFlux)
                .assertNext(customer -> {
                    System.out.println("customer : " + customer);
                    assertThat(customer.getBirth() < 20).isEqualTo(true);
                })
                .expectComplete()
                .verify(Duration.ofSeconds(30));
    }

    @Test
    public void givenCustomerExample_whenFind_thenFound() {
        reactiveRepository.save(new Customer(null, "kim", 1999090909)).block();

        // 전부 일치하게 넣어야지 찾아짐, name만 넣을 경우는 못 찾음
        // 다른 객체로 만들어서 넣을 순 없다
        Customer query = new Customer(null, "kim", 1999090909);
        Example<Customer> example = Example.of(query);

        Flux<Customer> fluxFound = reactiveRepository.findAll(example);

        StepVerifier.create(fluxFound)
                .assertNext(customer -> {
                    System.out.println("customer : " + customer);
                    assertThat(customer.getName()).isEqualTo(query.getName());
                    assertThat(customer.getBirth()).isEqualTo(query.getBirth());
                })
                .expectComplete()
                .verify(Duration.ofSeconds(30));
    }
}
