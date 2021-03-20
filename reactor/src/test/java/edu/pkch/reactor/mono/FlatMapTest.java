package edu.pkch.reactor.mono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class FlatMapTest {

    @Test
    void flatMap() {
        // given & when
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        System.out.println(Thread.currentThread().getName());
        Flux<Integer> actual = Flux.just(1, 2, 3, 4, 5)
                .map(number -> {
                    System.out.println("map: " + Thread.currentThread().getName());
                    return number;
                })
                .flatMap(number -> fetchSquareNumber().map(square -> number * square))
                .subscribeOn(Schedulers.fromExecutor(executorService));

        StepVerifier.create(actual)
                .expectNext(2)
                .expectNext(4)
                .expectNext(6)
                .expectNext(8)
                .expectNext(10)
                .verifyComplete();
    }

    private static Mono<Integer> fetchSquareNumber() {
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
            return Mono.just(2);
        } catch (InterruptedException e) {
            return Mono.just(2);
        }
    }

    @Test
    void name() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Flux.merge(testFlux(), Flux.just(11, 12, 13))
                .flatMap(Flux::just)
                .log()
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .subscribe();
    }

    private static Flux<Integer> testFlux() {
        try {
            Thread.sleep(2000);
            return Flux.just(1, 2, 3);
        } catch (InterruptedException e) {
            return Flux.just(1, 2, 3);
        }
    }
}
