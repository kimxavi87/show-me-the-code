package com.company.designpattern.decorator.impl;

import com.company.designpattern.decorator.Beverage;
import com.company.designpattern.decorator.ComdimentDecorator;

public class Mocha extends ComdimentDecorator {
    private Beverage beverage;

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public int cost() {
        return beverage.cost() + 400;
    }
}
