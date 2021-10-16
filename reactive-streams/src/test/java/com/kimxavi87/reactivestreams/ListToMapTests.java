package com.kimxavi87.reactivestreams;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ListToMapTests {

    @Test
    public void listToMapWithStream() {
        Map<String, Integer> collect = Arrays.asList(new Person("kim", 10), new Person("lee", 20)).stream()
                .collect(Collectors.toMap(person -> person.getName(), person -> person.getAge()));

        collect.forEach((k, v) -> System.out.println(k + " : " + v));
    }

    @Getter
    @AllArgsConstructor
    public static class Person {
        private String name;
        private int age;
    }
}
