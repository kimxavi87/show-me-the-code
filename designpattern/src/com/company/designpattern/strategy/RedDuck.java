package com.company.designpattern.strategy;

import com.company.designpattern.strategy.impl.FlyWithWings;
import com.company.designpattern.strategy.impl.Quack;

public class RedDuck extends Duck {
    public RedDuck() {
        flyBehavior = new FlyWithWings();
        quackBehavior= new Quack();
    }

    @Override
    public void display() {
        System.out.println("RED");
    }
}
