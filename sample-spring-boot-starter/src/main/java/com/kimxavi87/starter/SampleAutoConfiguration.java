package com.kimxavi87.starter;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SampleConfigurationProperties.class)
public class SampleAutoConfiguration {

    private final SampleConfigurationProperties sampleConfigurationProperties;

    public SampleAutoConfiguration(SampleConfigurationProperties sampleConfigurationProperties) {
        this.sampleConfigurationProperties = sampleConfigurationProperties;
    }

    @Bean
    public String somethingBean() {
        return "something bean : " + sampleConfigurationProperties.getOption();
    }
}
