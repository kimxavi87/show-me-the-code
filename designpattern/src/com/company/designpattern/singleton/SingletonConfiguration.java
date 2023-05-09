package com.company.designpattern.singleton;

public class SingletonConfiguration {
    private volatile static SingletonConfiguration instance;

    private SingletonConfiguration() {
    }

    public static SingletonConfiguration getInstance() {
        if (instance == null) {
            synchronized (SingletonConfiguration.class) {
                if (instance == null) {
                    instance = new SingletonConfiguration();
                }
            }
        }

        return instance;
    }

    public void getSomeProperty() {
        System.out.println("abc=bb");
    }
}
