package com.kimxavi87.spring.cannotaccess;

public abstract class AbstractRoot {

    public void method() {
        System.out.println("root method");
        start();
    }

    protected abstract void start();
}
