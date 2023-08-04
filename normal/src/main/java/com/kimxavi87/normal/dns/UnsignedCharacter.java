package com.kimxavi87.normal.dns;

public class UnsignedCharacter extends UnsignedValue {
    protected UnsignedCharacter(char value) {
        super(value, 1);
    }

    public char getChar() {
        return (char) this.getValue();
    }
}
