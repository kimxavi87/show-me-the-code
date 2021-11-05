package com.kimxavi87.spring.conf;

import org.springframework.stereotype.Component;

@Component
public class InjectProperties {
    private long id;
    public InjectProperties(MyProperties myProperties) {
        this.id = myProperties.getId();
    }

    public long getId() {
        return id;
    }
}