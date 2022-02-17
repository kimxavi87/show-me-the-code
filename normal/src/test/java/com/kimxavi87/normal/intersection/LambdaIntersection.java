package com.kimxavi87.normal.intersection;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

public class LambdaIntersection {

    @Test
    public void test() {
        run((DelegateTo<String> & Hello & UpperCase)() -> "World!", o -> {
            o.hello();
            o.upper();
        });
    }

    static <T extends DelegateTo<S>, S> void run(T t, Consumer<T> consumer) {
        consumer.accept(t);
    }

    interface DelegateTo<T> {
        T delegate();
    }

    interface Hello extends DelegateTo<String> {
        default void hello() {
            System.out.println("Hello " + delegate());
        }
    }

    interface UpperCase extends DelegateTo<String> {
        default void upper() {
            System.out.println(delegate().toUpperCase());
        }
    }
}
