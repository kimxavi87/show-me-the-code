package com.kimxavi87.springbatch.stepbystep.step;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class SomethingItemWriter implements ItemWriter<String> {
    @Override
    public void write(List<? extends String> items) throws Exception {
        for (String item : items) {
            System.out.println(item);
        }
    }
}
