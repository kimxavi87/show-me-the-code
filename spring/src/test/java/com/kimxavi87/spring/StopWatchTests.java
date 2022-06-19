package com.kimxavi87.spring;

import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

public class StopWatchTests {

    @Test
    public void stopwatch() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        stopWatch.stop();
    }
}
