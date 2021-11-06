package com.kimxavi87.aspectj;

import com.kimxavi87.aspectj.aspect.Hello;

public class Main {
    public static void main(String[] args) {
        Hello hello = new Hello();
        hello.hello();
    }
}
