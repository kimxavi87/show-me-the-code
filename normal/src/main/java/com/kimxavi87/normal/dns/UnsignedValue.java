package com.kimxavi87.normal.dns;

public abstract class UnsignedValue {
    private long value;
    private final long maximumValue;
    private final int bytes;

    protected UnsignedValue(long value, int bytes) {
        if (bytes > 4) {
            throw new IllegalArgumentException("bytes");
        }

        this.maximumValue = 1L << (bytes * 8L);
        checkRange(value);

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

    public void setBit(int pos) {
        this.value |= shiftValue(pos, 1L);
    }

    public void unsetBit(int pos) {
        this.value &= getMask(pos, 1);
    }

    public void setBits(int pos, int bitsRange, long value) {
        this.value &= getMask(pos, bitsRange);
        this.value |= shiftValue(pos + (bitsRange - 1), value);
    }

    public String toBinaryString() {
        return Long.toBinaryString(getValue());
    }

    private long getMask(int pos, int bitsRange) {
        long mask = 1L;

        for (int i = 0; i < bitsRange - 1; i++) {
            mask <<= 1;
            mask |= 1;
        }

        mask = shiftValue(pos + (bitsRange - 1), mask);

        return ~mask;
    }

    private long shiftValue(int pos, long value) {
        int bitPosition = bytes * 8 - pos - 1;
        if (pos < 0 || bitPosition < 0) {
            throw new IllegalArgumentException("out of range (pos)");
        }

        return value << bitPosition;
    }

    private void checkRange(long value) {
        if (!(value >= 0 && value < maximumValue)) {
            throw new IllegalArgumentException("out of range (value)");
        }
    }
}
