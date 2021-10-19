package com.kimxavi87.reactivestreams;

import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Slf4j
@SpringBootApplication
public class ReactiveStreams {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveStreams.class, args);
    }

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

        var initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ByteArrayResource(("CREATE SEQUENCE primary_key;"
                + "DROP TABLE IF EXISTS product;"
                + "CREATE TABLE product (id SERIAL PRIMARY KEY, name VARCHAR(100) NOT NULL);")
                .getBytes())));

        return initializer;
    }
}
