package com.company.designpattern.command.impl;

import com.company.designpattern.command.Command;
import com.company.designpattern.command.api.Light;

public class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.lightOn();
    }

    @Override
    public void undo() {
        light.lightOff();
    }
}
