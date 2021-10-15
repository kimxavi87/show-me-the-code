package com.kimxavi87.reactivestreams;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JustStreamTests {
    @Test
    public void whenStreamSizeIsZero() {
        List<Integer> list = new ArrayList<>();

        List<Integer> collect = list.stream()
                .map(i -> i * 2)
                .collect(Collectors.toList());

        Assert.assertEquals(0, collect.size());
    }

    @Test
    public void whenForeachAndListSizeZero() {
        List<Integer> list = new ArrayList<>();
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }
}
