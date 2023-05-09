package com.company.toby.reactivestreams;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ReactiveStreams {

    // call trace : pub.subscribe -> sub.onSubscribe -> subsc.request(pub) -> sub.onNext -> sub.onComplete
    public static void main(String[] args) {
        Publisher<Integer> publisher = pub();
        //Publisher<Integer> publisherOn = publishOn(publisher);
        // Publisher<Integer> mapPublisher = passPub(publisher);
        // Publisher<Integer> op1 = mapPub(publisher, i -> i * 10);
        // Publisher<Integer> op1 = reducePub(publisher, 0, (a,b) -> a+b);
        Publisher<Integer> subscribeOn = subscribeOn(publisher);
        subscribeOn.subscribe(sub());
        println("exit");
    }

    private static Publisher<Integer> subscribeOn(Publisher<Integer> publisher) {
        return subscriber -> {
            publisher.subscribe(new Subscriber<Integer>() {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                @Override
                public void onSubscribe(Subscription subscription) {
                    subscriber.onSubscribe(subscription);
                }

                @Override
                public void onNext(Integer item) {
                    executorService.execute(() ->subscriber.onNext(item));
                }

                @Override
                public void onError(Throwable throwable) {
                    executorService.execute(() ->subscriber.onError(throwable));
                    executorService.shutdown();
                }

                @Override
                public void onComplete() {
                    executorService.execute(() ->subscriber.onComplete());
                    executorService.shutdown();
                }
            });
        };
    }

    private static Publisher<Integer> publishOn(Publisher<Integer> publisher) {
        return new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> subscriber) {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(() ->publisher.subscribe(subscriber));
                //TODO 이것도 작업 완료후에 shutdown 하도록 수정
            }
        };
    }

    public static Publisher<Integer> reducePub(Publisher<Integer> publisher, int init, BiFunction<Integer, Integer, Integer> reduceFunction) {
        return new Publisher<Integer>() {
            int result = init;
            @Override
            public void subscribe(Subscriber<? super Integer> subscriber) {
                publisher.subscribe(new DelegateSubscriber<>(subscriber) {
                    @Override
                    public void onNext(Integer item) {
                        result = reduceFunction.apply(result, item);
                    }

                    @Override
                    public void onComplete() {
                        subscriber.onNext(result);
                        subscriber.onComplete();
                    }
                });
            }
        };
    }

    public static Publisher<Integer> mapPub(Publisher<Integer> publisher, Function<Integer, Integer> mapFunction) {
        return new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> subscriber) {
                publisher.subscribe(new DelegateSubscriber<Integer>(subscriber) {
                    @Override
                    public void onNext(Integer item) {
                        subscriber.onNext(mapFunction.apply(item));
                    }
                });
            }
        };
    }

    public static Publisher<Integer> passPub(Publisher<Integer> publisher) {
        return new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> subscriber) {
                publisher.subscribe(subscriber);
            }
        };
    }

    public static Subscriber<Integer> sub() {
        return new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                println("onSubscribe");
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer item) {
                println("onNext " + item);
            }

            @Override
            public void onError(Throwable throwable) {
                println(throwable.toString());
            }

            @Override
            public void onComplete() {
                println("onComplete");
            }
        };
    }

    public static Publisher<Integer> pub() {
        return new Publisher<>() {
            Iterable<Integer> iter = Arrays.asList(1, 2, 3, 4, 5, 6);

            @Override
            public void subscribe(Subscriber subscriber) {
                Iterator<Integer> iterator = iter.iterator();

                // must onSubscribe
                subscriber.onSubscribe(new Subscription() {
                    boolean isCancel = false;
                    @Override
                    public void request(long n) {
                        println("request()");
                        try {
                            for (int i = 0; i < n; i++) {
                                if (iterator.hasNext()) {
                                    subscriber.onNext(iterator.next());
                                } else {
                                    subscriber.onComplete();
                                    break;
                                }
                            }
                        } catch (RuntimeException e) {
                            subscriber.onError(e);
                        }
                    }

                    @Override
                    public void cancel() {
                    }
                });
            }
        };
    }

    public static void println(String s) {
        System.out.println(Thread.currentThread().getName() + " " + s);
    }
}
