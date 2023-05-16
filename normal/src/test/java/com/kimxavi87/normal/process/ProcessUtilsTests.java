package com.kimxavi87.normal.process;

import com.kimxavi87.normal.linux.LinuxCommands;
import com.kimxavi87.normal.linux.LinuxCommandsImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

public class ProcessUtilsTests {

    @Test
    @EnabledOnOs(OS.LINUX)
    public void test() {
        LinuxCommands linuxCommands = new LinuxCommandsImpl();
        linuxCommands.ps();
    }
}
