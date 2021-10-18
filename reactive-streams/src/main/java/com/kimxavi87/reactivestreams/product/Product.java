package com.kimxavi87.reactivestreams.product;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class Product {
    @Id
    private Long id;
    private String name;

    public Product(String name) {
        this.name = name;
    }
}
