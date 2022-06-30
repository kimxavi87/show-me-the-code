package com.kimxavi87.reactivestreams;

import com.kimxavi87.reactivestreams.conf.ShutdownQueue;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
@EnableR2dbcRepositories(basePackages = "com.kimxavi87.reactivestreams.product")
@SpringBootApplication
public class ReactiveStreams {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveStreams.class, args);

        ShutdownQueue.addDisposable(Flux.interval(Duration.ofSeconds(3)).subscribe());
        ShutdownQueue.addDisposable(Flux.interval(Duration.ofSeconds(5)).subscribe());
    }

    @Bean
    public Flux<Integer> testFlux() {
        return Flux.just(1, 2, 3, 4)
                .doOnNext(i -> System.out.println("testFlux : " + i));
    }

    // todo test 코드에서 에러 발생하는것 수정
//    @Bean
//    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
//
//        var initializer = new ConnectionFactoryInitializer();
//        initializer.setConnectionFactory(connectionFactory);
//        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ByteArrayResource((
//                "DROP TABLE IF EXISTS product;"
//                        + "CREATE TABLE product (id SERIAL PRIMARY KEY, name VARCHAR(100) NOT NULL);")
//                .getBytes())));
//
//        return initializer;
//    }
}
