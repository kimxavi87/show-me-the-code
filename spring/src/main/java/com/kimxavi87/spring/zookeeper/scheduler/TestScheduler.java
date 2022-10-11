package com.kimxavi87.spring.zookeeper.scheduler;

import com.kimxavi87.spring.zookeeper.Leader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestScheduler {

    @Leader
    @Scheduled(cron = "0/1 * * * * *")
    public void test() throws InterruptedException {
        System.out.println("SCHEDULER START");
        Thread.sleep(1000 * 3);
        System.out.println("SCHEDULER END");
    }
}
