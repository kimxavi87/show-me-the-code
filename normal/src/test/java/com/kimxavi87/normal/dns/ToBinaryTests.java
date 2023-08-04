package com.kimxavi87.normal.dns;

import org.junit.jupiter.api.Test;

public class ToBinaryTests {

    @Test
    void test() {
        System.out.println(1 << 3);
        System.out.println(0xF);
        System.out.println(Long.toBinaryString(0x87FF));
        System.out.println(Long.toBinaryString(Long.MAX_VALUE));
    }
}
