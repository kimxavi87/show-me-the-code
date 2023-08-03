package com.kimxavi87.normal;

public class SimpleByteArray {
    private final byte[] byteArr;
    private int idx;

    public SimpleByteArray(int size) {
        this.idx = 0;
        this.byteArr = new byte[size];
    }

    public void add(byte b) {
        if (idx == byteArr.length) {
            throw new RuntimeException("Array is full");
        }
        byteArr[idx] = b;
        idx++;
    }

    public void add(byte[] byteArr) {
        for (byte b : byteArr) {
            add(b);
        }
    }

    public byte[] getByteArr() {
        byte[] out = new byte[idx];
        System.arraycopy(byteArr, 0, out, 0, idx);
        return out;
    }

    public int size() {
        return idx;
    }
}
