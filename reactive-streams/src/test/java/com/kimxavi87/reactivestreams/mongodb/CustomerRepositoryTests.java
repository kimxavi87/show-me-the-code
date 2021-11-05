package com.kimxavi87.reactivestreams.mongodb;

import com.kimxavi87.reactivestreams.Customer.Customer;
import com.kimxavi87.reactivestreams.Customer.CustomerCrudRepository;
import com.kimxavi87.reactivestreams.Customer.CustomerReactiveRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CustomerRepositoryTests {
    @Autowired
    private CustomerCrudRepository crudRepository;
    @Autowired
    private CustomerReactiveRepository reactiveRepository;

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
}
