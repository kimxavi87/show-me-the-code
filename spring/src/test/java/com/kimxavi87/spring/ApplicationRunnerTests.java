package com.kimxavi87.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ApplicationRunnerTests {
    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void gwt() {
        // ApplicationRunner
        assertThat(Application.isRunning.get()).isEqualTo(true);
        assertThat(Application.isRunningForBean.get()).isEqualTo(true);

        // ApplicationListener
        // JUnit 테스트 코드에서도 ApplicationReadyEvent 이벤트는 발생
        assertThat(Application.isRunningForEvent.get()).isEqualTo(true);

        // main은 실행 안 되는군
        assertThat(Application.isMain.get()).isEqualTo(false);
        assertThat(Application.isRunningForMain.get()).isEqualTo(false);
    }

    @Test
    public void test() {
        com.kimxavi87.spring.TestComponent bean = applicationContext.getBean(TestComponent.class);
        System.out.println(bean.getClass());
    }
}
