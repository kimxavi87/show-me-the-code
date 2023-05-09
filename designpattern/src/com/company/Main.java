package com.company;

import com.company.designpattern.builder.NyPizza;
import com.company.designpattern.builder.Pizza;
import com.company.designpattern.command.RemoteController;
import com.company.designpattern.command.api.Light;
import com.company.designpattern.command.impl.LightOffCommand;
import com.company.designpattern.command.impl.LightOnCommand;
import com.company.designpattern.decorator.Beverage;
import com.company.designpattern.decorator.Espresso;
import com.company.designpattern.decorator.impl.Mocha;
import com.company.designpattern.decorator.impl.Whip;
import com.company.designpattern.factory.store.NyPizzaStore;
import com.company.designpattern.factory.PizzaStore;
import com.company.designpattern.flyweight.Circle;
import com.company.designpattern.flyweight.ShapeFactory;
import com.company.designpattern.observer.ThirdPartyDisplay;
import com.company.designpattern.observer.WeatherData;
import com.company.designpattern.singleton.SingletonConfiguration;
import com.company.designpattern.strategy.Duck;
import com.company.designpattern.strategy.RedDuck;

public class Main {

    public static void main(String[] args) {

        ShapeFactory shapeFactory = new ShapeFactory();
        Circle red = shapeFactory.getCircle("red");
        red.setRadius(5);
        red.setX(10);
        red.setY(5);
        red.draw();

        Circle blue = shapeFactory.getCircle("blue");
        blue.setRadius(10);
        blue.setX(15);
        blue.setY(0);
        blue.draw();

        Circle redAgain= shapeFactory.getCircle("red");
        System.out.println(red == redAgain);

        redAgain.setRadius(10);
        redAgain.setX(10);
        redAgain.setY(5);
        redAgain.draw();

        NyPizza nyPizza = new NyPizza.Builder(5)
                .addTopping(Pizza.Topping.HAM)
                .addTopping(Pizza.Topping.MUSHROOM)
                .build();
        System.out.println(nyPizza.toString());

        Duck duck = new RedDuck();
        duck.performFly();
        duck.performQuack();

        RemoteController remoteController = new RemoteController();
        Light light = new Light();
        remoteController.setCommand(0, new LightOnCommand(light), new LightOffCommand(light));
        remoteController.pressOnButton(0);
        remoteController.pressOffButton(0);
        remoteController.pressUndoButton();

        SingletonConfiguration singletonConfiguration = SingletonConfiguration.getInstance();
        singletonConfiguration.getSomeProperty();

        PizzaStore pizzaStore = new NyPizzaStore();
        pizzaStore.orderPizza("cheese");

        Beverage beverage = new Whip(new Mocha(new Espresso()));
        System.out.println("cost: " + beverage.cost());

        WeatherData weatherData = new WeatherData();
        ThirdPartyDisplay display = new ThirdPartyDisplay(weatherData);
        weatherData.setMesurements("35C", "22%", "1000p");
        weatherData.setMesurements("25C", "12%", "500p");
    }
}
