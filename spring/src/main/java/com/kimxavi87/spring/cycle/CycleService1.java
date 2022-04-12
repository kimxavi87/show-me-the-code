package com.kimxavi87.spring.cycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
//@Service
public class CycleService1 {
    private final CycleService2 cycleService2;

    public CycleService1(CycleService2 cycleService2) {
        log.info("CONST cycle1");
        this.cycleService2 = cycleService2;
    }
}
