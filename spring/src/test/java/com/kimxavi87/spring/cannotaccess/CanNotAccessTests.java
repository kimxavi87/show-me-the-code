package com.kimxavi87.spring.cannotaccess;

import org.junit.jupiter.api.Test;

public class CanNotAccessTests {
    @Test
    public void test() {
        Child child = new Child();
        child.method();
    }
}
