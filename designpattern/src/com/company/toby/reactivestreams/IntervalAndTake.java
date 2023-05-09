package com.company.toby.reactivestreams;

import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IntervalAndTake {
    public static void main(String[] args) {
        Flow.Publisher<Integer> interval = (subscriber) -> {
            subscriber.onSubscribe(new Flow.Subscription() {
                int i = 0;
                boolean isCanceled = false;
                @Override
                public void request(long n) {
                    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
                    scheduledExecutorService.scheduleAtFixedRate(() -> {
                        if (isCanceled) {
                            scheduledExecutorService.shutdown();
                            return;
                        }
                        subscriber.onNext(i++);
                    }, 0, 300, TimeUnit.MILLISECONDS);
                }

                @Override
                public void cancel() {
                    isCanceled = true;
                }
            });
        };

        int takeNumber = 10;

        Flow.Publisher<Integer> take = new Flow.Publisher<>() {
            int count = 0;
            @Override
            public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
                interval.subscribe(new Flow.Subscriber<Integer>() {
                    Flow.Subscription inSubsc;
                    @Override
                    public void onSubscribe(Flow.Subscription subscription) {
                        subscriber.onSubscribe(subscription);
                        this.inSubsc = subscription;
                    }

                    @Override
                    public void onNext(Integer item) {
                        subscriber.onNext(item);
                        if (++count >= takeNumber) {
                            inSubsc.cancel();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onError(throwable);
                    }

                    @Override
                    public void onComplete() {
                        subscriber.onComplete();
                    }
                });


            }
        };

        Flow.Subscriber<Integer> subscriber = new Flow.Subscriber<Integer>() {
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                System.out.println("onSubscribe");
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer item) {
                System.out.println("onNext " + item);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError " + throwable);
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");

            }
        };

        take.subscribe(subscriber);
    }
}
