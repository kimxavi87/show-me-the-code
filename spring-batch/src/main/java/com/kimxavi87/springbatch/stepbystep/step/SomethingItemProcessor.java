package com.kimxavi87.springbatch.stepbystep.step;

import org.springframework.batch.item.ItemProcessor;

public class SomethingItemProcessor implements ItemProcessor<String, String> {
    @Override
    public String process(String item) throws Exception {
        return item.toUpperCase();
    }
}
