package com.kimxavi87.normal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MockTableTests {

    @Test
    void mockTable() {
        MockTable<TestModel> mockTable = new MockTable<>(new TypeReference<>() {});
        TestModel model = TestModel.builder()
                .name("namename")
                .otherId(2)
                .build();

        TestModel added = mockTable.add(model);
        System.out.println(added.getId());
        System.out.println(added.getName());

        List<TestModel> all = mockTable.findAll();
        System.out.println(all);

        TestModel oneByCondition = mockTable.findOneByCondition(m -> m.id == added.id);
        System.out.println(oneByCondition);

        TestModel nullDeleted = mockTable.deleteById(2);
        System.out.println(nullDeleted);

        TestModel deleted = mockTable.deleteById(1);
        System.out.println(deleted);
        System.out.println(mockTable.findAll());
    }

    public static class MockTable<T> {
        private static final ObjectMapper objectMapper = new ObjectMapper();
        private static final TypeReference<Map<String, Object>> MAP_TYPE_REF = new TypeReference<Map<String, Object>>() {};

        private final List<Map<String, Object>> data;
        private final TypeReference<T> typeReference;
        private long serial;


        public MockTable(TypeReference<T> typeReference) {
            this.typeReference = typeReference;
            this.data = new ArrayList<>();
            this.serial = 1;
            System.out.println(typeReference.getType().getTypeName());
        }

        public T add(T object) {
            Map<String, Object> map = objectToMap(object);
            if (!map.containsKey("id") || (Long) map.get("id") == 0L) {
                map.put("id", serial);
            }

            data.add(map);
            serial++;
            return mapToObject(map);
        }

        private T mapToObject(Map<String, Object> map) {
            return objectMapper.convertValue(map, typeReference);
        }

        private Map<String, Object> objectToMap(T object) {
            return objectMapper.convertValue(object, MAP_TYPE_REF);
        }

        private T findOneByCondition(Predicate<T> predicate) {
            for (Map<String, Object> datum : data) {
                T willCheckObject = mapToObject(datum);

                if (predicate.test(willCheckObject)) {
                    return willCheckObject;
                }
            }

            return null;
        }

        public List<T> findAll() {
            return data.stream()
                    .map(this::mapToObject)
                    .collect(Collectors.toList());
        }

        public T deleteById(long id) {
            for (int i = 0; i < data.size(); i++) {
                Map<String, Object> datum = data.get(i);
                if (datum.get("id") != null && (Long) datum.get("id") == id) {
                    return mapToObject(data.remove(i));
                }
            }

            return null;
        }
    }

    @ToString
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TestModel {
        private long id;
        private long otherId;
        private String name;
    }
}
