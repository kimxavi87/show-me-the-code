package com.kimxavi87.reactivestreams.product;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table
public class Product {
    @Id
    private Long id;
    private String name;

    public Product(String name) {
        this.name = name;
    }
}
