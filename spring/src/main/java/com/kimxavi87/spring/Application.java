package com.kimxavi87.spring;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.atomic.AtomicBoolean;

@SpringBootApplication
public class Application implements ApplicationRunner {
    public static final AtomicBoolean isRunning = new AtomicBoolean(false);
    public static final AtomicBoolean isRunningForBean = new AtomicBoolean(false);
    public static final AtomicBoolean isMain = new AtomicBoolean(false);
    public static final AtomicBoolean isRunningForMain = new AtomicBoolean(false);

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        Application application = applicationContext.getBean(Application.class);
        application.start();

        isMain.compareAndSet(false, true);
    }

    public void start() {
        isRunningForMain.compareAndSet(false, true);
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
