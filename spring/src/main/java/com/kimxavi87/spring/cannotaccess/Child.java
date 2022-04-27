package com.kimxavi87.spring.cannotaccess;

public class Child extends AbstractMiddle {
    @Override
    protected void middle() {
        System.out.println("Child middle");
    }
}
