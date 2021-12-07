package com.kimxavi87.reactivestreams.customer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Sharded;

@ToString
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Sharded(shardKey = {"name", "birth"})
@Document
public class Customer {
    @Id
    private String id;
    private String name;
    private int birth;
}
