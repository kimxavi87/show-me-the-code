package com.kimxavi87.springbatch.stepbystep.step;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ListItemReader implements ItemReader<String> {
    public static final List<String> list = Arrays.asList("abc", "dDef", "EqeFd", "asde");
    private Iterator<String> iterator;

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (iterator == null) {
            iterator = list.iterator();
        }

        if (iterator.hasNext()) {
            return iterator.next();
        }

        return null;
    }
}
