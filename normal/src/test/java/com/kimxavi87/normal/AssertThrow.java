package com.kimxavi87.normal;

public class AssertThrow {
    private final Exception exception;

    public AssertThrow(Exception e) {
        this.exception = e;
    }

    public Exception getException() {
        return exception;
    }
}
