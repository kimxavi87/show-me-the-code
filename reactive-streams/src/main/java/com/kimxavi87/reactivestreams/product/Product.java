package com.kimxavi87.reactivestreams.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
@AllArgsConstructor
public class Product {
    @Id
    private Long id;
    private String name;
}
