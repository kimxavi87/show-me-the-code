package com.kimxavi87.springbatch.stepbystep;

import com.kimxavi87.springbatch.stepbystep.step.ListItemReader;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ItemReader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemReaderTests {

    @Test
    public void givenStringListItemReader_whenReadItem_thenReturnString() throws Exception {
        ItemReader<String> itemReader = new ListItemReader();
        List<String> list = ListItemReader.list;
        int i = 0;
        while (true) {
            String read = itemReader.read();

            if (list.size() == i) {
                assertThat(read).isEqualTo(null);
                break;
            }

            assertThat(read).isEqualTo(list.get(i++));
        }
    }
}
