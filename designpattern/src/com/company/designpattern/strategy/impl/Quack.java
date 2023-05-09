package com.company.designpattern.strategy.impl;

import com.company.designpattern.strategy.QuackBehavior;

public class Quack implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("Quack");
    }
}
