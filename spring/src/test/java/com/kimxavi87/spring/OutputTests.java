package com.kimxavi87.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OutputCaptureExtension.class)
public class OutputTests {

    @Test
    public void sout(CapturedOutput output) {
        String outputString = "Hello World!";
        System.out.println(outputString);

        // isEqualTo 로 테스트하지 않고, contains로 대부분 테스트 함
        assertThat(output.getOut()).contains(outputString);
    }
}
