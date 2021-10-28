package com.kimxavi87.springbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.run(args);

        System.out.println(springApplication.getWebApplicationType());
        // batch 는 WebApplicationType.None
        // application 실행 바로 끝남
    }
}
