package com.songi.reacitveprogramming.toby_study.reactive01;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Flow.*;

public class PubSub {
    public static void main(String[] args) throws InterruptedException {
        // Publisher <- Observable
        // Subscriber <- Observer
        Iterable<Integer> iter = Arrays.asList(1, 2, 3, 4, 5);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        // db 에서 가져온 iterable 데이터라 생각
        Publisher pub = new Publisher() {

            @Override
            public void subscribe(Subscriber subscriber) {
                Iterator<Integer> it = iter.iterator();
                // 구독 방식
                subscriber.onSubscribe(new Subscription() {
                    @Override
                    public void request(long n) {
                        executor.execute(() -> {
                            // 진행상황을 체크하고 싶을 때 Future(자바 비동기의 결과정보) 로
                            Future<?> f = executor.submit(() -> {
                                int i = 0;
                                try {
                                    while (i++ < n) {
                                        if (it.hasNext()) { // 데이터가 있으면 가져와
                                            subscriber.onNext(it.next());
                                        } else {
                                            subscriber.onComplete(); // 없으면 onComplete 하자.
                                            break;
                                        }
                                    }
                                } catch (RuntimeException e) {
                                    subscriber.onError(e);
                                }
                            });
                        });

                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        };

        Subscriber<Integer> sub = new Subscriber<Integer>() {
            Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("onSubscribe");
                this.subscription = subscription;
                this.subscription.request(1);
            }

            @Override
            public void onNext(Integer item) {
                // update
                System.out.println(Thread.currentThread().getName() + " on Next " + item);
                this.subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                // observer 에는 없던 error
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                // observer 에는 없던 complete
                System.out.println("onComplete");
            }
        };

        pub.subscribe(sub);

        executor.awaitTermination(10, TimeUnit.HOURS);
        executor.shutdown();
    }
}
