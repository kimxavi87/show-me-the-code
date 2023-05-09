package com.company.designpattern.factory;

public class NySeaFactory extends AbstractPizzaFactory {
    @Override
    public String createDough() {
        return "thin";
    }

    @Override
    public String createSauce() {
        return "tomato";
    }
}
