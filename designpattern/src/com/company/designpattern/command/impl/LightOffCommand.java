package com.company.designpattern.command.impl;

import com.company.designpattern.command.Command;
import com.company.designpattern.command.api.Light;

public class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.lightOff();
    }

    @Override
    public void undo() {
        light.lightOn();
    }
}
