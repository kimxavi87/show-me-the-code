package com.kimxavi87.spring.cycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
//@Service
public class CycleService2 {
    private final CycleService1 cycleService1;

    public CycleService2(CycleService1 cycleService1) {
        log.info("CONST cycle2");
        this.cycleService1 = cycleService1;
    }
}
