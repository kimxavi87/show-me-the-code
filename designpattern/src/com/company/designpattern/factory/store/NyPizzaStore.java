package com.company.designpattern.factory.store;

import com.company.designpattern.factory.NySeaFactory;
import com.company.designpattern.factory.PizzaStore;
import com.company.designpattern.factory.pizza.NyCheesePizza;
import com.company.designpattern.factory.pizza.NyPeperoniPizza;
import com.company.designpattern.factory.Pizza;

public class NyPizzaStore extends PizzaStore {
    @Override
    public Pizza createPizza(String type) {
        if ("cheese".equalsIgnoreCase(type)) {
            return new NyCheesePizza(new NySeaFactory());
        } else if ("peperoni".equalsIgnoreCase(type)) {
            return new NyPeperoniPizza(new NySeaFactory());
        } else {
            throw new RuntimeException("Wrong type Pizza");
        }
    }
}
