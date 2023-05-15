package com.kimxavi87.normal.linux;

import lombok.Getter;

@Getter
public class ProcessInfo {
    private final int pid;
    private final String name;
    private final String command;

    public ProcessInfo(int pid, String name, String command) {
        this.pid = pid;
        this.name = name;
        this.command = command;
    }
}
