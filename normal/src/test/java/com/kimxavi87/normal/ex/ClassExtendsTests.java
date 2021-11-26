package com.kimxavi87.normal.ex;

import org.junit.jupiter.api.Test;

public class ClassExtendsTests {

    @Test
    public void classExtendsConst() {
        Child child = new Child();
    }

    public static class Parents {
        public Parents() {
            System.out.println("P con");
        }
    }

    public static class Child extends Parents {
        public Child() {
            System.out.println("child");
        }
    }
}
