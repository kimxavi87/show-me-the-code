package com.kimxavi87.spring.cycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CycleService3 {
    @Autowired
    private CycleService4 cycleService4;

    public void service() {
        log.info("service3 method");
    }
}
