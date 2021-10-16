package com.kimxavi87.reactivestreams.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductRepository repository;

    @GetMapping("/products")
    public Flux<Product> requestProductList(@PageableDefault(size = 10) Pageable pageable) {
        return repository.findAll();
    }
}
