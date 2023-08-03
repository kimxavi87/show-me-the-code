package com.kimxavi87.normal.dns;

public abstract class UnsignedValue {
    private final long value;
    private final int bytes;

    protected UnsignedValue(long value, int bytes) {
        if (bytes > 4) {
            throw new IllegalArgumentException("bytes");
        }

        long maximumValue = 1L << (bytes * 8L);
        if (!(value >= 0 && value < maximumValue)) {
            throw new IllegalArgumentException("out of range");
        }

        this.bytes = bytes;
        this.value = value & maximumValue - 1;
    }

    public long getValue() {
        return value;
    }

    public byte[] toByteArray() {
        byte[] bytesArr = new byte[bytes];
        int mask = 0;
        for (int i = bytes - 1; i >= 0; i--) {
            bytesArr[i] = (byte) ((value >>> mask) & 0xFF);
            mask += 8;
        }

        return bytesArr;
    }
}
