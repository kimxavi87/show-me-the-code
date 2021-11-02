package com.kimxavi87.spring.util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class CollectionsTests {
    @Test
    public void hashSet() {
        // hashSet add 는 hashMap key에 밸류를 넣는구나..
        HashSet<SomethingNo> setNoHashEquals= new HashSet<>();

        setNoHashEquals.add(new SomethingNo("abc", 10));
        setNoHashEquals.add(new SomethingNo("abc", 10));
        // 위처럼 내부 값이 같은 인스턴스를 넣었지만, 중복이라 판단하지 않고 1이 아닌 2를 반환한다
        assertThat(setNoHashEquals.size()).isEqualTo(2);

        HashSet<SomethingWithHashcodeAndEquals> setWithHashEquals = new HashSet<>();

        setWithHashEquals.add(new SomethingWithHashcodeAndEquals("abc", 10));
        setWithHashEquals.add(new SomethingWithHashcodeAndEquals("abc", 10));
        // hashcode 와 equals 구현을 통해 중복이 제거된 모습
        assertThat(setWithHashEquals.size()).isEqualTo(1);

        // 내부적으로 Node 객체 배열의 table을 가짐
        // 처음에는 null 로 있다가 put 메서드가 호출되면
        // resize() 호출해서 할당
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("key", "value");
    }

    private static class SomethingNo {
        private String string;
        private int integer;

        public SomethingNo(String string, int integer) {
            this.string = string;
            this.integer = integer;
        }
    }

    private static class SomethingWithHashcodeAndEquals {
        private String string;
        private int integer;

        public SomethingWithHashcodeAndEquals(String string, int integer) {
            this.string = string;
            this.integer = integer;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SomethingWithHashcodeAndEquals something = (SomethingWithHashcodeAndEquals) o;
            return integer == something.integer &&
                    Objects.equals(string, something.string);
        }

        @Override
        public int hashCode() {
            return Objects.hash(string, integer);
        }
    }
}
