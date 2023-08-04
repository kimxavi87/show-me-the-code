package com.kimxavi87.normal.dns;

import org.junit.jupiter.api.Test;

public class UnsignedValueTests {

    @Test
    void test_1byte() {
        // 1byte
        UnsignedValue unsignedValue = new DummyUnsignedValue(2, 1);
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
        UnsignedValue twoBytes = new DummyUnsignedValue(32767, 2);
        byte[] bytes = twoBytes.toByteArray();
        System.out.println(bytes.length);
        System.out.println(bytes[0]);
        System.out.println(bytes[1]);
    }

    @Test
    void test_4byte() {
        UnsignedValue fourBytes = new DummyUnsignedValue(32767, 4);
        System.out.println(fourBytes.getValue());
    }

    public static class DummyUnsignedValue extends UnsignedValue {

        protected DummyUnsignedValue(long value, int bytes) {
            super(value, bytes);
        }
    }

    @Test
    void unsigned_char() {
        UnsignedShort unsignedShort = new UnsignedShort(255);
        System.out.println(unsignedShort.getValue());

        UnsignedCharacter unsignedCharacter = new UnsignedCharacter('C');
        System.out.println(unsignedCharacter.getValue());
        System.out.println(unsignedCharacter.getChar());

        AssertThrow assertThrow = assertThrow(() -> {
            UnsignedCharacter unsignedCharacter2 = new UnsignedCharacter((char) 256);
            System.out.println(unsignedCharacter2.getValue());
        });

        assert assertThrow.getException() instanceof IllegalArgumentException;
    }

    private AssertThrow assertThrow(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            return new AssertThrow(e);
        }
        return null;
    }

    private static class AssertThrow {
        private final Exception exception;

        private AssertThrow(Exception e) {
            this.exception = e;
        }

        public Exception getException() {
            return exception;
        }
    }
}
