package com.kimxavi87.reactivestreams;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.converter.MessagingMessageConverter;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.condition.EmbeddedKafkaCondition;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderResult;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@EmbeddedKafka(topics = EmbeddedKafkaTests.TOPIC,
        brokerProperties = { "transaction.state.log.replication.factor=1", "transaction.state.log.min.isr=1" })
public class EmbeddedKafkaTests {
    public static final String TOPIC = "test-topic";
    private static final String CONSUMER_GROUP_ID = "test-consumer-group";
    private static final Duration DEFAULT_VERIFY_TIMEOUT = Duration.ofSeconds(10);
    private static final String DEFAULT_VALUE = "test-value";

    private static ReactiveKafkaConsumerTemplate<Integer, String> kafkaConsumer;
    private ReactiveKafkaProducerTemplate<Integer, String> kafkaProducer;
    private static final EmbeddedKafkaBroker broker = EmbeddedKafkaCondition.getBroker();

    @BeforeAll
    public static void setUpBeforeClass() {

        Map<String, Object> consumerProps =
                KafkaTestUtils.consumerProps(CONSUMER_GROUP_ID, "false", broker);
        consumerProps.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 5);

        kafkaConsumer =
                new ReactiveKafkaConsumerTemplate<>(setupReceiverOptionsWithDefaultTopic(consumerProps));

//        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
//        logger.setLevel(Level.OFF);
    }

    @BeforeEach
    public void setUp() {
        kafkaProducer = new ReactiveKafkaProducerTemplate<>(setupSenderOptionsWithDefaultTopic(),
                new MessagingMessageConverter());
    }

    private SenderOptions<Integer, String> setupSenderOptionsWithDefaultTopic() {
        Map<String, Object> senderProps =
                KafkaTestUtils.producerProps(EmbeddedKafkaCondition.getBroker().getBrokersAsString());
        return SenderOptions.create(senderProps);
    }

    private static ReceiverOptions<Integer, String> setupReceiverOptionsWithDefaultTopic(
            Map<String, Object> consumerProps) {

        ReceiverOptions<Integer, String> basicReceiverOptions = ReceiverOptions.create(consumerProps);
        return basicReceiverOptions
                .consumerProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
                .addAssignListener(p -> assertThat(p.iterator().next().topicPartition().topic())
                        .isEqualTo(TOPIC))
                .subscription(Collections.singletonList(TOPIC));
    }

    @AfterEach
    public void tearDown() {
        kafkaProducer.close();
    }

    @Test
    public void givenStringValue_whenSendRecord_thenReceiveRecord() {
        Mono<SenderResult<Void>> senderResultMono =
                this.kafkaProducer.send(TOPIC, DEFAULT_VALUE);

        StepVerifier.create(senderResultMono)
                .assertNext(senderResult -> {
                    assertThat(senderResult.recordMetadata())
                            .extracting(RecordMetadata::topic)
                            .isEqualTo(TOPIC);
                })
                .expectComplete()
                .verify(DEFAULT_VERIFY_TIMEOUT);

        StepVerifier
                .create(kafkaConsumer
                        .receive()
                        .doOnNext(rr -> System.out.println(rr.value()))
                        .doOnNext(rr -> rr.receiverOffset().acknowledge()))
                .assertNext(receiverRecord -> assertThat(receiverRecord.value()).isEqualTo(DEFAULT_VALUE))
                .thenCancel()
                .verify(DEFAULT_VERIFY_TIMEOUT);
    }

    @Test
    public void givenStringValue_whenHappenException_thenKeepReceive() throws InterruptedException {
        Flux.interval(Duration.ofSeconds(1))
                .flatMap(l -> this.kafkaProducer.send(TOPIC, DEFAULT_VALUE))
//                .doOnNext(voidSenderResult -> System.out.println(voidSenderResult.recordMetadata().topic()))
                .subscribe();
        // commit 하지 않으면 poll 되지 않음
        // 다른 쓰레드로 작업을 계속 진행해도 poll, commit 을 하지 않음

        // map 안에서 exception 던지면
        // cancel 이나 complete 가 발생하지 않고
        // error 발생
        // consume도 더 하지 않는다 (문제 상황 확인 완료)
        // catch 하면 계속 진행됨
        kafkaConsumer.receiveAutoAck()
//                .publishOn(Schedulers.newSingle("hello-world-"))
                .doOnNext(consumerRecord -> log.info("[CONSUME] {}", consumerRecord.toString()))
                .map(rr -> {
                    try {
                        Thread.sleep(1000 * 10);
                        log.info("[CONSUME] AFTER SLEEP");
                    } catch (InterruptedException e) {
                        return "";
                    }
                    log.info("[CONSUME] PROCESS ");
                    return "";
//                    try {
//                        throw new RuntimeException("Hello World!");
//                    } finally {
//                        System.out.println("Hello Finally");
//                        rr.receiverOffset().acknowledge();
//                    }
                })
                .onErrorContinue((e, o) -> log.error("error", e))
                .doOnNext(s -> System.out.println("MAPMAP"))
                .subscribe();
//        kafkaConsumer.receiveAutoAck()
//                .doOnNext(consumerRecord -> System.out.println(consumerRecord.toString()))
////                .map(consumerRecord -> {
////                    try {
////                        throw new RuntimeException("Hello World!");
////                    } catch (Exception e) {
////                        System.out.println(e.getCause());
////                    }
////                    return "";
////                })
//                .map(consumerRecord -> {
//                    throw new RuntimeException("Hello Error!");
//                })
//                .doOnCancel(() -> System.out.println("cancel"))
//                .doOnComplete(() -> System.out.println("complete"))
//                .subscribe();

        Thread.sleep(5000 * 1000);
    }
}
