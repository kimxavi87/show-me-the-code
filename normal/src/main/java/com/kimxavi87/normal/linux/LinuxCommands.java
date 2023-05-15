package com.kimxavi87.normal.linux;

import lombok.Getter;
import org.apache.commons.lang3.SystemUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LinuxCommands {
    private LinuxCommands() {
    }

    public static List<ProcessInfo> ps() {
        if (!isLinux()) {
            throw new UnsupportedOperationException();
        }

        return getProcesses();

    }

    private static List<ProcessInfo> getProcesses() {
        List<ProcessInfo> processes = new ArrayList<>();
        File procDir = new File("/proc");

        if (!procDir.exists()) {
            throw new UnsupportedOperationException();
        }

        String[] pids = procDir.list((dir, name) -> name.matches("\\d+"));

        if (pids == null || pids.length == 0) {
            return Collections.emptyList();
        }

        for (String pid : pids) {
            String statusPath = "/proc/" + pid + "/status";
            String cmdlinePath = "/proc/" + pid + "/cmdline";

            try (BufferedReader statusReader = new BufferedReader(new FileReader(statusPath));
                 BufferedReader cmdlineReader = new BufferedReader(new FileReader(cmdlinePath))) {
                String name = statusReader.readLine().split(":")[1].trim();
                String command = cmdlineReader.readLine();
                processes.add(new ProcessInfo(Integer.parseInt(pid), name, command));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return processes;
    }

    private static boolean isLinux() {
        return SystemUtils.IS_OS_LINUX;
    }

    @Getter
    public static class ProcessInfo {
        private final int pid;
        private final String name;
        private final String command;

        public ProcessInfo(int pid, String name, String command) {
            this.pid = pid;
            this.name = name;
            this.command = command;
        }
    }
}
