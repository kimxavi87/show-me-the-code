package com.kimxavi87.normal;

public class AssertTools {
    private AssertTools() {
    }

    public static AssertThrow assertThrow(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            return new AssertThrow(e);
        }
        return null;
    }
}
