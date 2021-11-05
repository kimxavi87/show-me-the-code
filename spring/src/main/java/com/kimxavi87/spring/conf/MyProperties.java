package com.kimxavi87.spring.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

// @Configuration 없으면 문법 에러로 잡고, Setter 도 없으면 Excpetion 발생
@Getter
@Setter
@Configuration
// 없는 properties 면 그냥 초기값으로 들어감, 예외는 발생 안함
@ConfigurationProperties("my")
public class MyProperties {
    private long id;
}
