package com.kimxavi87.reactivestreams.reactor;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
public class BufferWIndowTests {

    @Test
    void window() {
        log.info("START");
        Flux.range(0, 10)
                .map(i -> i * 2)
                .doOnNext(integer -> log.info("{}", integer))
                .window(4)
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(integerFlux ->
                        integerFlux.map(integer -> integer * 3)
                                .doOnNext(integer -> log.info("list {}", integer))
                                .subscribe())
                .doOnNext(integer -> log.info("after {}", integer))
                .subscribe();
        log.info("END");
    }

    @Test
    void buffer() {
        log.info("START");
        Flux.range(0, 10)
                .map(i -> i * 2)
                .doOnNext(integer -> log.info("{}", integer))
                .buffer(4)
                .doOnNext(integers -> log.info("list {}", integers))
                .log()
                .subscribe();
        log.info("END");
    }

    @Test
    void groupBy() {
        Flux.range(0, 10)
                .groupBy(this::mapper)
                .log()
                .map(integerIntegerGroupedFlux -> integerIntegerGroupedFlux
                        .doOnNext(integers -> log.info("list {}", integers))
                        .map(integers -> integers)
                )
                .subscribe();

        Flux.just(
                        new Item("A", "1"), new Item("B", "1"),
                        new Item("A", "2"), new Item("B", "2"),
                        new Item("A", "3"), new Item("B", "3"),
                        new Item("A", "4"), new Item("B", "4")
                )
                .groupBy(item -> item.category) // 카테고리별로 그룹화
                .flatMap(groupedFlux ->
                        groupedFlux.buffer(2) // 각 그룹 내에서 2개씩 묶기
                                .map(list -> groupedFlux.key() + ": " + list)
                )
                .subscribe(System.out::println);
    }

    @Test
    void groupBy_test() {
        Flux.range(0, 100)
                .map(i -> (int)(Math.random() * 100) + 1)
                .map(this::makePerson)
                .groupBy(person -> person.getCity())
                .flatMap(stringPersonGroupedFlux -> stringPersonGroupedFlux.groupBy(person -> person.getState()))
                .flatMap(stringPersonGroupedFlux -> stringPersonGroupedFlux
                        .buffer(4)
                        .map(person -> {
                            log.info("{}", person);
                            return 0;
                        })
                )
                .subscribe();
    }

    @Test
    void groupBy_with_key() {
        Flux.range(0, 100)
                .map(i -> {
                    if (i == 50) {
                        try {
                            log.info("-------------SLEEP---------");
                            Thread.sleep(10000L);
                        } catch (InterruptedException e) {
                        }
                    }

                    return i;
                })
                .map(i -> (int)(Math.random() * 100) + 1)
                .map(this::makePersonItem)
                .groupBy(this::makeKeyItem)
                .flatMap(stringPersonGroupedFlux -> stringPersonGroupedFlux
                        .buffer(4)
                        .map(person -> {
                            KeyItem key = stringPersonGroupedFlux.key();
                            log.info("{} {}", key, person);
                            for (PersonItem personItem : person) {
                                if (personItem.getKeyType() != key.getKeyType()
                                        || !personItem.getCode().equals(key.getCode())) {
                                    System.out.println("--------------ERROR----------------");
                                }
                            }
                            return 0;
                        })
                )
                .subscribe();
    }

    private KeyItem makeKeyItem(PersonItem person) {
        return new KeyItem(person.getCode(), person.getKeyType());
    }

    private PersonItem makePersonItem(int i) {
        if (i % 2 == 0) {
            return new PersonItem("park", i, "AAA", KeyType.A);
        } else if (i % 3 == 0) {
            return new PersonItem("kim", i, "BBB", KeyType.B);
        } else if (i % 5 == 0) {
            return new PersonItem("ho", i, "CCC", KeyType.A);
        } else {
            return new PersonItem("lee", i, "DDD", KeyType.B);
        }
    }

    @EqualsAndHashCode
    @ToString
    @RequiredArgsConstructor
    @Getter
    static class KeyItem {
        private final String code;
        private final KeyType keyType;
    }

    enum KeyType {
        A, B
    }

    private Person makePerson(int i) {
        if (i % 2 == 0) {
            return new Person("park", "seoul", "gangnam", i);
        } else if (i % 3 == 0) {
            return new Person("kim", "busan", "haeundae", i);
        } else if (i % 5 == 0) {
            return new Person("ho", "seoul", "seocho", i);
        } else {
            return new Person("lee", "busan", "gwang", i);
        }
    }

    int mapper(int i) {
        if (i % 2 == 0) {
            return 2;
        } else {
            return 3;
        }
    }

    @ToString
    @AllArgsConstructor
    @Getter
    static class PersonItem {
        private String name;
        private int age;
        private String code;
        private KeyType keyType;
    }

    @ToString
    @AllArgsConstructor
    @Getter
    static class Person {
        private String name;
        private String city;
        private String state;
        private int age;
    }

    static class Item {
        String category;
        String value;

        Item(String category, String value) {
            this.category = category;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Item{" + "category='" + category + '\'' + ", value='" + value + '\'' + '}';
        }
    }
}
