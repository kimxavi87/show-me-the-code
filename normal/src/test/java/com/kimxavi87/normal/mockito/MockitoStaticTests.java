package com.kimxavi87.normal.mockito;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class MockitoStaticTests {

    @Test
    void staticMethod_withCapture() {
        try (MockedStatic<TestStaticMethodClass> utilitiesMock = Mockito.mockStatic(TestStaticMethodClass.class)) {

            ArgumentCaptor<String> argCaptor = ArgumentCaptor.forClass(String.class);

            AppClass caller = new AppClass();
            caller.testMethod("test");

            utilitiesMock.verify(() -> TestStaticMethodClass.staticMethod(argCaptor.capture()));

            // test@
            System.out.println(argCaptor.getValue());
        }
    }

    static class TestStaticMethodClass {
        public static void staticMethod(String name) {

        }
    }

    static class AppClass {
        public void testMethod(String name) {
            TestStaticMethodClass.staticMethod(name + "@");
        }
    }
}
