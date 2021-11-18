package com.kimxavi87.reactivestreams;

import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

// package org.springframework.web.reactive.function.client.WebClientIntegrationTests 참고함
// https://github.com/spring-projects/spring-framework/blob/main/spring-webflux/src/test/java/org/springframework/web/reactive/function/client/WebClientIntegrationTests.java
@Slf4j
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

    @Test
    public void whenBuildWithUriBuilder_thenRequest() {
        int firstPathVar = 2;
        String secondPathVar = "VIP";

        prepareResponse(mockResponse -> mockResponse
                .setBody("Ok"));

        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/customer/{id}/type/{type}")
                        .build(firstPathVar, secondPathVar))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        expectRequest(recordedRequest -> {
            assertThat(recordedRequest.getRequestUrl().toString().contains(String.valueOf(firstPathVar)))
                    .isEqualTo(true);
            assertThat(recordedRequest.getRequestUrl().toString().contains(secondPathVar))
                    .isEqualTo(true);
        });

        // takeRequest 는 요청이 오는걸 기다린다. 즉 response 1개 넣으면 request도 한 개
        // takeRequest 를 2번 호출하면 한번은 기다림
    }

    @Test
    public void givenMany_whenRequest_thenCheckHowItWorks() throws InterruptedException {
        // 10 개를 요청해도 nio 쓰레드가 10개로 늘어나진 않음 : 현재 8개로 표시됨
        // 비동기 8개 미만 :  5초 뒤에 응답 오고 끝남
        // 동기 10개 : 50초 걸림
        // 비동기 16개 : thread pool * 2 개수 만큼 요청해도 동일하게 끝남

        Flux<String> ok = Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
                .doOnNext(i -> prepareResponse(mockResponse -> mockResponse
                        .setBodyDelay(5, TimeUnit.SECONDS)
                        .setBody("Ok" + i)))
                .doOnNext(integer -> log.info("log + {}", integer))
                .flatMap(integer -> {
                    return webClient.get()
                            .retrieve()
                            .bodyToMono(String.class);
                })
                .doOnNext(s -> log.info(s));
        // WebClient 요청 전에는 다 main thread
        // WebClient 요청 즉 flatMap 끝난 뒤에 doOnNext 도 reactor-http-nio- 쓰레드 풀에서 실행됨

        StepVerifier.create(ok)
                .expectNextCount(16)
                .expectComplete()
                .verify(Duration.ofSeconds(60));
    }

    private void prepareResponse(Consumer<MockResponse> consumer) {
        MockResponse response = new MockResponse();
        consumer.accept(response);
        this.server.enqueue(response);
    }

    private void expectRequest(Consumer<RecordedRequest> consumer) {
        try {
            consumer.accept(this.server.takeRequest());
        }
        catch (InterruptedException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
