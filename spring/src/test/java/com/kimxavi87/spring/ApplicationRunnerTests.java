package com.kimxavi87.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ApplicationRunnerTests {

    @Test
    public void gwt() {
        assertThat(Application.isRunning.get()).isEqualTo(true);
        assertThat(Application.isRunningForBean.get()).isEqualTo(true);
        // main은 실행 안 되는군
        assertThat(Application.isMain.get()).isEqualTo(false);
        assertThat(Application.isRunningForMain.get()).isEqualTo(false);
    }
}
