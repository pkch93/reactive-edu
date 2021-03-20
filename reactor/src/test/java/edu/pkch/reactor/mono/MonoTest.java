package edu.pkch.reactor.mono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class MonoTest {

    @Test
    void practice() {
        Mono<Double> mono = Mono.just(2)
                .map(number -> Math.pow(number, 5))
                .map(Math::floor);

        mono.subscribe(System.out::println);
    }

    @Test
    void practice2() {
        // given & when
        Mono<List<Integer>> actual = Mono.just(List.of(1, 2, 3, 4, 5))
                .map(numbers -> numbers.stream()
                        .filter(Odd::isOdd)
                        .map(Odd::of)
                        .collect(Collectors.toList())
                )
                .flatMap(odds -> fetchSquareNumber()
                        .map(square ->
                                odds.stream()
                                        .map(odd -> odd.pow(square))
                                        .collect(Collectors.toList())
                        )
                )
                .onErrorReturn(Collections.emptyList());

        // then
        StepVerifier.create(actual)
                .expectNext(List.of(1, 9, 25))
                .verifyComplete();
    }

    @Test
    void practice3() {
        // given & when
        Mono<List<Integer>> actual = Mono.just(List.of(1, 2, 3, 4, 5))
                .map(numbers -> numbers.stream()
                        .filter(Odd::isOdd)
                        .map(Odd::of)
                        .collect(Collectors.toList())
                )
                .flatMap(odds -> fetchSquareNumber()
                        .map(square ->
                                odds.stream()
                                        .map(odd -> odd.pow(square))
                                        .collect(Collectors.toList())
                        )
                )
                .timeout(Duration.ofMillis(100))
                .onErrorReturn(Collections.emptyList());

        // then
        StepVerifier.create(actual)
                .expectNext(Collections.emptyList())
                .verifyComplete();
    }

    private static Mono<Integer> fetchSquareNumber() {
        try {
            Thread.sleep(200);
            return Mono.just(2);
        } catch (InterruptedException e) {
            return Mono.error(TimeoutException::new);
        }
    }

    static class Odd {
        private final int value;

        private Odd(int value) {
            this.value = value;
        }

        public static Odd of(int value) {
            return new Odd(value);
        }

        public static boolean isOdd(int value) {
            return value % 2 == 1;
        }

        public int pow(int sqaure) {
            int result = value;
            for (int i = 1; i < sqaure; i++) {
                result *= value;
            }
            return result;
        }

        public int getValue() {
            return value;
        }
    }

    @Test
    void name() {

    }
}
