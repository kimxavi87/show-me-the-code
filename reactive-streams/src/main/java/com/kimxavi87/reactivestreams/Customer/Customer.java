package com.kimxavi87.reactivestreams.Customer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Document
public class Customer {
    @Id
    private String id;
    private String name;
    private int birth;
}
