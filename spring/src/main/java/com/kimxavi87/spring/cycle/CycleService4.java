package com.kimxavi87.spring.cycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CycleService4 {
    @Autowired
    private CycleService3 cycleService3;

    public void service() {
        log.info("service 4 method");
        cycleService3.service();
    }
}
