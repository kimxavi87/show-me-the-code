package com.kimxavi87.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiredArgsConstructor
@EnableCaching
@SpringBootApplication
public class Application implements ApplicationRunner {
    public static final AtomicBoolean isRunning = new AtomicBoolean(false);
    public static final AtomicBoolean isRunningForBean = new AtomicBoolean(false);
    public static final AtomicBoolean isMain = new AtomicBoolean(false);
    public static final AtomicBoolean isRunningForMain = new AtomicBoolean(false);
    public static final AtomicBoolean isRunningForEvent = new AtomicBoolean(false);
    private final ApplicationArguments applicationArguments;

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        Application application = applicationContext.getBean(Application.class);
        application.start();

        isMain.compareAndSet(false, true);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        isRunningForEvent.compareAndSet(false, true);
    }

    public void start() {
        isRunningForMain.compareAndSet(false, true);
        System.out.println(Arrays.toString(applicationArguments.getSourceArgs()));
        System.out.println(applicationArguments.getNonOptionArgs());
        System.out.println(applicationArguments.getOptionValues("myargs"));
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            isRunningForBean.compareAndSet(false, true);
        };
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        isRunning.compareAndSet(false, true);
    }
}
