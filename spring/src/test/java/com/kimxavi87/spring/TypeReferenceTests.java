package com.kimxavi87.spring;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;

public class TypeReferenceTests {

    @Test
    public void test() {
        TypeReference<Integer> integerTypeReference = new TypeReference<>(){};

        System.out.println(integerTypeReference.getType());

        Operator<Integer> operator = new Operator<>(integerTypeReference);
        operator.method();
    }

    public static class Operator<T> {
        private final TypeReference<T> typeReference;

        public Operator(TypeReference<T> typeReference) {
            this.typeReference = typeReference;
        }

        public void method() {
            TypeReference<T> typeReference = new TypeReference<>() {};
            System.out.println(typeReference.getType());
            System.out.println(this.typeReference.getType());
        }
    }
}
