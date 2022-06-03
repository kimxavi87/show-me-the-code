package com.kimxavi87.spring.conf;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ApplicationArgumentsConfiguration {
    private final ApplicationArguments applicationArguments;

    @Bean
    public String appString() {
        // 테스트 코드에서도 bean 생성돼서 할당됨
        System.out.println(applicationArguments.getOptionNames());
        // 물론 args 는 받아들여지지 않음
        System.out.println(applicationArguments.getOptionValues("myargs"));

        return "HelloWorld!";
    }
}
