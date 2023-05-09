package com.company.designpattern.command;

public class RemoteController {
    private Command[] onCommands;
    private Command[] offCommands;
    private Command undoCommand;

    private final static NoCommand NO_COMMAND = new NoCommand();

    public RemoteController() {
        onCommands = new Command[7];
        offCommands = new Command[7];

        for (int i = 0; i < 7; i++) {
            onCommands[i] = NO_COMMAND;
            offCommands[i] = NO_COMMAND;
        }
        undoCommand = NO_COMMAND;
    }

    public void setCommand(int slot, Command onCommand, Command offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }

    public void pressOnButton(int slot) {
        onCommands[slot].execute();
        undoCommand = onCommands[slot];
    }

    public void pressOffButton(int slot) {
        offCommands[slot].execute();
        undoCommand = offCommands[slot];
    }

    public void pressUndoButton() {
        undoCommand.undo();
        undoCommand = NO_COMMAND;
    }
}
