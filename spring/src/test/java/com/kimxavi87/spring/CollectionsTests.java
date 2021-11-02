package com.kimxavi87.spring;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

public class CollectionsTests {
    @Test
    public void hashSet() {
        // hashSet add 는 hashMap key에 밸류를 넣는구나..
        HashSet<Object> objects = new HashSet<>();

        // 내부적으로 Node 객체 배열의 table을 가짐
        // 처음에는 null 로 있다가 put 메서드가 호출되면
        // resize() 호출해서 할당
        HashMap<Object, Object> hashMap = new HashMap<>();
    }
}
