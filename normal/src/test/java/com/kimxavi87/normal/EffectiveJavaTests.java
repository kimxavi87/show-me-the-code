package com.kimxavi87.normal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class EffectiveJavaTests {

    @DisplayName("p.177 제너릭 싱글턴 팩터리")
    @Test
    public void test() {
        // 불변 객체를 여러 타입으로 활용할 수 있도록 하는 예시
        Collections.reverseOrder();
        Collections.emptySet();
    }
}
