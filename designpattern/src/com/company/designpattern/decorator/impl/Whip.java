package com.company.designpattern.decorator.impl;

import com.company.designpattern.decorator.Beverage;
import com.company.designpattern.decorator.ComdimentDecorator;

public class Whip extends ComdimentDecorator {
    private Beverage beverage;

    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public int cost() {
        return beverage.cost() + 500;
    }
}
