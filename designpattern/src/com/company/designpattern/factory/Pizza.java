package com.company.designpattern.factory;

public abstract class Pizza {
    protected String name;
    protected String dough;
    protected String sauce;

    public abstract void prepare();

    public void bake() {
        System.out.println("bake 15m");

    }

    public void cut() {
        System.out.println("cut 8");
    }

    public void box() {
        System.out.println("square box");
    }
}
