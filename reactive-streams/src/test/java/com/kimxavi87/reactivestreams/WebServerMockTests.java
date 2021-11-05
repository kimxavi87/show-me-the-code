package com.kimxavi87.reactivestreams;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

// package org.springframework.web.reactive.function.client.WebClientIntegrationTests 참고함
public class WebServerMockTests {
    private MockWebServer server;
    private WebClient webClient;

    @BeforeEach
    public void setUp() throws IOException {
        server = new MockWebServer();
        server.start();
        webClient = WebClient
                .builder()
                .baseUrl(server.url("/").toString())
                .build();
    }

    @AfterEach
    public void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void whenConnectWebServer_thenResponse200Ok() {
        prepareResponse(mockResponse -> mockResponse
                .setBodyDelay(2L, TimeUnit.SECONDS)
                .setBody("Ok"));

        Mono<String> response = webClient.get()
                .retrieve()
                .bodyToMono(String.class);

        StepVerifier.create(response)
                .expectNext("Ok")
                .expectComplete()
                .verify(Duration.ofSeconds(4L));
    }

    @Test
    public void whenPostMethod_thenResponse200Ok() throws InterruptedException {
        String body = "Hello World!";

        prepareResponse(mockResponse -> mockResponse
                .setBodyDelay(2L, TimeUnit.SECONDS)
                .setBody("Ok"));

        webClient.post()
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        assertThat(this.server.takeRequest().getBody().toString().contains(body))
                .isEqualTo(true);
    }

    private void prepareResponse(Consumer<MockResponse> consumer) {
        MockResponse response = new MockResponse();
        consumer.accept(response);
        this.server.enqueue(response);
    }
}
