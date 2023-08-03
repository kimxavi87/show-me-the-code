package com.kimxavi87.normal;

public class SimpleByteArray {
    private byte[] byteArr;
    private int idx;

    public SimpleByteArray() {
        this(16);
    }

    public SimpleByteArray(int size) {
        this.idx = 0;
        this.byteArr = new byte[size];
    }

    public void add(byte b) {
        if (idx == byteArr.length) {
            resize(byteArr.length * 2);
        }
        byteArr[idx] = b;
        idx++;
    }

    public void resize(int size) {
        if (size() > size) {
            throw new IllegalArgumentException();
        }

        byte[] newArr = new byte[size];
        System.arraycopy(byteArr, 0, newArr, 0, idx);
        byteArr = newArr;
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

    public int length() {
        return byteArr.length;
    }
}
