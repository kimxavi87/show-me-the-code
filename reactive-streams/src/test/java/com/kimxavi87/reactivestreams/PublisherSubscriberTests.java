package com.kimxavi87.reactivestreams;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Flow;

@Slf4j
public class PublisherSubscriberTests {
    @Test
    public void pubSub() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        Flow.Publisher<Object> publisher = new Flow.Publisher<>() {
            private Iterator<Integer> iterator = integers.iterator();

            @Override
            public void subscribe(Flow.Subscriber<? super Object> subscriber) {
                subscriber.onSubscribe(new Flow.Subscription() {
                    @Override
                    public void request(long n) {
                        try {
                            while (n-- > 0) {
                                if (iterator.hasNext()) {
                                    subscriber.onNext(iterator.next());
                                } else {
                                    subscriber.onComplete();
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        };

        Flow.Subscriber<Object> subscriber = new Flow.Subscriber<>() {
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                subscription.request(Integer.MAX_VALUE);
            }

            @Override
            public void onNext(Object item) {
                log.info("onNext : {}", item);
            }

            @Override
            public void onError(Throwable throwable) {
                log.info("onError : ", throwable);
            }

            @Override
            public void onComplete() {
                log.info("onComplete");
            }
        };

        publisher.subscribe(subscriber);
    }
}
