package com.kimxavi87.normal.enumm;

import org.junit.jupiter.api.Test;

public class EnumTests {
    public static enum TestEnum {
        A,B,C;
    }

    @Test
    public void test() {
        // IllegalArgumentException 발생함
        TestEnum.valueOf("D");
    }
}
