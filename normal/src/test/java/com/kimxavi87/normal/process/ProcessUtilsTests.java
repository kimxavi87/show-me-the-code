package com.kimxavi87.normal.process;

import com.kimxavi87.normal.linux.LinuxCommands;
import com.kimxavi87.normal.linux.LinuxCommandsImpl;
import org.junit.jupiter.api.Test;

public class ProcessUtilsTests {

    @Test
    public void test() {
        LinuxCommands linuxCommands = new LinuxCommandsImpl();
        linuxCommands.ps();
    }
}
