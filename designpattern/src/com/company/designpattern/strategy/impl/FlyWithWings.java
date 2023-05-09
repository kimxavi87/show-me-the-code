package com.company.designpattern.strategy.impl;

import com.company.designpattern.strategy.FlyBehavior;

public class FlyWithWings implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("Fly with Wings");
    }
}
