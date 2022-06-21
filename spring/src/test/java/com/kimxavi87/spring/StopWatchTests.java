package com.kimxavi87.spring;

import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;

public class StopWatchTests {

    @Test
    public void stopwatch() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println(stopWatch.getTotalTimeMillis());
        Thread.sleep(1000);
        System.out.println(stopWatch.getTotalTimeMillis());
        stopWatch.stop();
    }

    @Test
    public void newStopWatch() throws InterruptedException {
        StopWatchLap stopWatchLap = new StopWatchLap();
        stopWatchLap.start();
        stopWatchLap.lap();
        Thread.sleep(1000);
        stopWatchLap.lap();
        Thread.sleep(1000);
        stopWatchLap.stop();
        System.out.println(stopWatchLap);
    }

    public static class StopWatchLap {
        private long startMilliTime;
        private long endMilliTime;
        private long beforeMilliTime;
        private final List<Long> lap = new ArrayList<>();

        public void start() {
            startMilliTime = System.currentTimeMillis();
            beforeMilliTime = startMilliTime;
        }

        public void lap() {
            long now = System.currentTimeMillis();
            lap.add(now - beforeMilliTime);
            beforeMilliTime = now;
        }

        public void stop() {
            endMilliTime = System.currentTimeMillis();
        }

        public void reset() {
            startMilliTime = 0;
            beforeMilliTime = 0;
            lap.clear();
        }

        @Override
        public String toString() {
            return String.format("TotalTime=%d, lap=%s", endMilliTime - startMilliTime, lap);
        }
    }
}
