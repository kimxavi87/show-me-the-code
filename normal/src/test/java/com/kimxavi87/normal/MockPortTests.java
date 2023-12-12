package com.kimxavi87.normal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class MockPortTests {
    @Test
    void test() {
        FinalObject parkk = FinalObject.builder()
                .name("park")
                .age(32)
                .build();
        System.out.println(parkk);

        FinalObject kim = FinalObject.builder()
                .name("kim")
                .age(20)
                .build();

        MockPort mockPort = new MockPort();

        FinalObject afterSave = mockPort.save(parkk);
        mockPort.save(kim);

        System.out.println(afterSave);

        System.out.println(mockPort.findAll());

        mockPort.deleteById(1);

        System.out.println(mockPort.findAll());
    }

    @ToString
    @Getter
    @Builder
    @AllArgsConstructor
    public static class FinalObject {
        private final long id;
        private final String name;
        private final int age;
    }

    public static class MockPort extends AbstractMockPort<FinalObject> {
        @Override
        protected FinalObject create(FinalObject object, long serial) {
            return FinalObject.builder()
                    .id(object.getId() == 0 ? serial : object.getId())
                    .age(object.getAge())
                    .name(object.getName())
                    .build();
        }

        public FinalObject save(FinalObject finalObject) {
            return super.add(finalObject);
        }

        public List<FinalObject> findAll() {
            return super.findAll();
        }
    }

    public abstract static class AbstractMockPort<T> {
        private final List<T> data = new ArrayList<>();
        private long serial = 1;

        protected abstract T create(T object, long serial);

        public T add(T object) {
            T newOne = create(object, serial);
            data.add(newOne);
            serial++;
            return newOne;
        }

        private T findOneByCondition(Predicate<T> predicate) {
            for (T datum : data) {
                if (predicate.test(datum)) {
                    return datum;
                }
            }

            return null;
        }

        public List<T> findAll() {
            return data;
        }

        public T deleteById(long id) {
            for (int i = 0; i < data.size(); i++) {
                Object datum = data.get(i);
                Long objectId = getId(datum);
                if (objectId != null && objectId == id) {
                    return data.remove(i);
                }
            }

            return null;
        }

        private Long getId(Object o) {
            try {
                Class<?> clazz = o.getClass();
                Field secretField = clazz.getDeclaredField("id");
                secretField.setAccessible(true);
                return (Long) secretField.get(o);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                return null;
            }
        }
    }
}
