package com.kimxavi87.reactivestreams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.converter.MessagingMessageConverter;
import org.springframework.kafka.test.condition.EmbeddedKafkaCondition;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderResult;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@EmbeddedKafka(topics = EmbeddedKafkaTests.TOPIC,
        brokerProperties = { "transaction.state.log.replication.factor=1", "transaction.state.log.min.isr=1" })
public class EmbeddedKafkaTests {
    public static final String TOPIC = "test-topic";
    private static final int DEFAULT_PARTITION = 1;
    private static final String CONSUMER_GROUP_ID = "test-consumer-group";
    private static final Duration DEFAULT_VERIFY_TIMEOUT = Duration.ofSeconds(10);
    private static final String DEFAULT_VALUE = "test-value";

    private static ReactiveKafkaConsumerTemplate<Integer, String> kafkaConsumer;
    private ReactiveKafkaProducerTemplate<Integer, String> kafkaProducer;

    @BeforeAll
    public static void setUpBeforeClass() {
        Map<String, Object> consumerProps =
                KafkaTestUtils.consumerProps(CONSUMER_GROUP_ID, "false", EmbeddedKafkaCondition.getBroker());
        kafkaConsumer =
                new ReactiveKafkaConsumerTemplate<>(setupReceiverOptionsWithDefaultTopic(consumerProps));
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
}