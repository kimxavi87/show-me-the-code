package com.company.designpattern.factory.pizza;

import com.company.designpattern.factory.AbstractPizzaFactory;
import com.company.designpattern.factory.Pizza;

public class NyPeperoniPizza extends Pizza {
    private AbstractPizzaFactory pizzaFactory;

    public NyPeperoniPizza(AbstractPizzaFactory pizzaFactory) {
        this.pizzaFactory = pizzaFactory;
    }

    @Override
    public void prepare() {
        dough = pizzaFactory.createDough();
        sauce = pizzaFactory.createSauce();
    }
}
