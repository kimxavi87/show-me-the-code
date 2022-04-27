package com.kimxavi87.spring.cannotaccess;

public abstract class AbstractMiddle extends AbstractRoot {
    @Override
    protected void start() {
        System.out.println("abstract start");
        middle();
    }

    protected abstract void middle();
}
