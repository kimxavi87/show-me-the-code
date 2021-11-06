package com.kimxavi87.aspectj.aspect;

public aspect HelloAspect {

    void around(): call(void Hello.hello()) {
        System.out.println("before...");
        proceed();
        System.out.println("after...");
    }
}
