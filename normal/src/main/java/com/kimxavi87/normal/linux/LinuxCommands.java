package com.kimxavi87.normal.linux;

import java.util.List;

public interface LinuxCommands {
    List<ProcessInfo> ps();
    void netstat();
}
