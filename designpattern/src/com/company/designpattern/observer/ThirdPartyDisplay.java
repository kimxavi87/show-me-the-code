package com.company.designpattern.observer;

public class ThirdPartyDisplay implements Observer, DisplayElement {
    private Subject subject;
    private String temperature;
    private String humidity;
    private String pressure;

    public ThirdPartyDisplay(Subject subject) {
        this.subject = subject;
        this.subject.registerObserver(this);
    }

    @Override
    public void update(String temperature, String humidity, String pressure) {
        this.temperature = temperature + " : tempa";
        this.humidity = temperature + " : humi";
        this.pressure = temperature + " : pre";
        display();
    }

    @Override
    public void display() {
        System.out.println(temperature + " " + humidity + " " + pressure);
    }
}
