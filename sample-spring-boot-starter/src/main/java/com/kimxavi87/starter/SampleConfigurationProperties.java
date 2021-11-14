package com.kimxavi87.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("sample")
public class SampleConfigurationProperties {
    private String option;

    public String getOption() {
        return option;
    }
}
