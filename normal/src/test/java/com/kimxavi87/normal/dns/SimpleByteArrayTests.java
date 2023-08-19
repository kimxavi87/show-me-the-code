package com.kimxavi87.normal.dns;

import org.junit.jupiter.api.Test;

public class SimpleByteArrayTests {

    @Test
    void add() {
        SimpleByteArray simpleByteArray = new SimpleByteArray(3);
        simpleByteArray.add((byte) 1);
        simpleByteArray.add((byte) 2);
        simpleByteArray.add((byte) 3);

        assert simpleByteArray.length() == 3;

        simpleByteArray.add((byte) 4);

        assert simpleByteArray.length() != 3;

        byte[] byteArr = simpleByteArray.getByteArr();

        assert byteArr[0] == (byte) 1;
        assert byteArr[1] == (byte) 2;
        assert byteArr[2] == (byte) 3;
        assert byteArr[3] == (byte) 4;
    }
}
