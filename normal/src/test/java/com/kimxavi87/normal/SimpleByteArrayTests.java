package com.kimxavi87.normal;

import org.junit.jupiter.api.Test;

public class SimpleByteArrayTests {

    @Test
    void add() {
        SimpleByteArray simpleByteArray = new SimpleByteArray(3);
        simpleByteArray.add((byte) 1);
//        assertThat(simpleByteArray.size()).isEqualTo(1);
        simpleByteArray.add((byte) 2);
//        assertThat(simpleByteArray.size()).isEqualTo(2);
        simpleByteArray.add((byte) 3);
//        assertThat(simpleByteArray.size()).isEqualTo(3);

        byte[] byteArr = simpleByteArray.getByteArr();
        System.out.println(byteArr[0]);
        assert byteArr[0] == (byte) 1;
        assert byteArr[1] == (byte) 2;
        assert byteArr[2] == (byte) 3;
    }
}
