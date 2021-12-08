package com.kimxavi87.reactivestreams.webflux;

import com.kimxavi87.reactivestreams.product.Product;
import com.kimxavi87.reactivestreams.product.ProductController;
import com.kimxavi87.reactivestreams.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ProductController.class)
public class ProductControllerTests {
    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void controller() {
        Mockito.when(productRepository.findAll())
                .thenReturn(Flux.just(new Product("park")));

        webTestClient.get()
                .uri("/products")
                .exchange()
                .expectStatus().isOk()
                // todo arraylist 로 응답이 온다
                // todo typeReference로 받아야 함
                .expectBody(String.class)
                .value(System.out::println);
    }
}
