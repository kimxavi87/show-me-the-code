package com.company.toby.supertypetoken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class Generic {
    static class TypeSafeMap {
        // 어떤 타입이 올지 모르니 wildcard
        Map<TypeReference<?>, Object> innerMap = new HashMap<>();

        public <T> void put(TypeReference<T> key, Object value) {
            innerMap.put(key, value);
        }

        public <T> T get(TypeReference<T> key) {
            if (key.type instanceof Class<?>) {
                return ((Class<T>) key.type).cast(innerMap.get(key));
            } else {
                return ((Class<T>) ((ParameterizedType) key.type).getRawType()).cast(innerMap.get(key));
            }
        }
    }
    static class TypeReference<T> {
        Type type;
        protected TypeReference() {
            Type sType = this.getClass().getGenericSuperclass();
            if (sType instanceof ParameterizedType) {
                type = ((ParameterizedType) sType).getActualTypeArguments()[0];
            } else {
                throw new RuntimeException("Not Supported Type");
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass().getSuperclass() != o.getClass().getSuperclass()) return false;
            TypeReference<?> that = (TypeReference<?>) o;
            return Objects.equals(type, that.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type);
        }
    }

    public static void main(String[] args) {

        TypeSafeMap typeSafeMap = new TypeSafeMap();
        typeSafeMap.put(new TypeReference<Integer>() {}, 5);
        typeSafeMap.put(new TypeReference<List<Integer>>() {}, Arrays.asList(5, 3, 4));

        System.out.println(typeSafeMap.get(new TypeReference<Integer>() {}));
        System.out.println(typeSafeMap.get(new TypeReference<List<Integer>>() {}));

    }
}
