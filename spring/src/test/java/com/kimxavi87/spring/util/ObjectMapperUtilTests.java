package com.kimxavi87.spring.util;

import com.kimxavi87.spring.utils.ObjectMapperUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectMapperUtilTests {

    @Test
    public void mapToJsonString() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", "aa");
        map.put("b", 12);
        String json = ObjectMapperUtil.mapToJsonString(map);
        System.out.println(json);
        assertThat(json).isEqualTo("{\"a\":\"aa\",\"b\":12}");
    }
}
