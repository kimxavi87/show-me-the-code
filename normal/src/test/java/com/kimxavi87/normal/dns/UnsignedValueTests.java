package com.kimxavi87.normal.dns;

import org.junit.jupiter.api.Test;

public class UnsignedValueTests {

    @Test
    void test_1byte() {
        // 1byte
        UnsignedValue unsignedValue = new UnsignedValue(2, 1);
        System.out.println(unsignedValue.getValue());
        byte[] bytes = unsignedValue.toByteArray();
        System.out.println(bytes.length);
        System.out.println(bytes[0]);

//        UnsignedValue minus1 = new UnsignedValue(-1, 1);
//        UnsignedValue oor256 = new UnsignedValue(256, 1);
    }

    @Test
    void test_2byte() {
        // 2byte
        UnsignedValue twoBytes = new UnsignedValue(32767, 2);
        byte[] bytes = twoBytes.toByteArray();
        System.out.println(bytes.length);
        System.out.println(bytes[0]);
        System.out.println(bytes[1]);
    }

    @Test
    void test_4byte() {
        UnsignedValue fourBytes = new UnsignedValue(32767, 4);
        System.out.println(fourBytes.getValue());
    }
}
