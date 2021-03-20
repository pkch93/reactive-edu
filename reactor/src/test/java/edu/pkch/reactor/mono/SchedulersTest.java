package edu.pkch.reactor.mono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

class SchedulersTest {

    @Test
    void immediate() throws InterruptedException {
        // given
        Scheduler scheduler = Schedulers.immediate();

        // when & then
        Thread thread = new Thread(() -> {
            System.out.println("current thread: " + Thread.currentThread().getName());

            Mono.just("helloworld!")
                    .map(msg -> msg + " pkch!")
                    .subscribeOn(scheduler)
                    .subscribe(msg -> System.out.println("subscribe thread: " + Thread.currentThread().getName()));
        });

        thread.start();
        thread.join();
    }

    @Test
    void single() throws InterruptedException {
        // given
        Scheduler scheduler = Schedulers.single();

        // when & then
        Thread thread = new Thread(() -> {
            System.out.println("current thread: " + Thread.currentThread().getName());

            Mono.just("helloworld!")
                    .map(msg -> msg + " pkch!")
                    .subscribeOn(scheduler)
                    .subscribe(msg -> System.out.println("subscribe thread: " + Thread.currentThread().getName()));
        });

        thread.start();
        thread.join();
    }

    @Test
    void boundedElastic() throws InterruptedException {
        // given
        Scheduler scheduler = Schedulers.boundedElastic();

        // when & then
        Thread thread = new Thread(() -> {
            System.out.println("current thread: " + Thread.currentThread().getName());

            Mono.just("helloworld!")
                    .map(msg -> msg + " pkch!")
                    .subscribeOn(scheduler)
                    .subscribe(msg -> System.out.println("subscribe thread: " + Thread.currentThread().getName()));
        });

        thread.start();
        thread.join();
    }

    @Test
    void parallel() throws InterruptedException {
        // given
        Scheduler scheduler = Schedulers.parallel();

        // when & then
        Thread thread = new Thread(() -> {
            System.out.println("current thread: " + Thread.currentThread().getName());

            Mono.just("helloworld!")
                    .map(msg -> msg + " pkch!")
                    .subscribeOn(scheduler)
                    .subscribe(msg -> System.out.println("subscribe thread: " + Thread.currentThread().getName()));
        });

        thread.start();
        thread.join();
    }

    @Test
    void publishOn() throws InterruptedException {
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> {
                    System.out.println("previous publishOn: " + Thread.currentThread().getName());
                    return 10 + i;
                })
                .publishOn(s)
                .filter(i -> {
                        System.out.println("after publishOn: " + Thread.currentThread().getName());
                        return i % 2 == 0;
                })
                .map(i -> {
                    System.out.println("after publishOn: " + Thread.currentThread().getName());
                    return "value " + i;
                });

        Thread thread = new Thread(() -> flux.subscribe(System.out::println));

        thread.start();
        thread.join();
    }
}
