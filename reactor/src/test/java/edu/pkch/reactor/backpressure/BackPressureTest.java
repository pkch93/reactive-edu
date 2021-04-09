package edu.pkch.reactor.backpressure;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

class BackPressureTest {

    @Test
    void createOnBackpressureDrop() {
        // given
        List<Integer> numbers = Arrays.asList(1, 2, 3);

        // when
        Flux<Integer> actual = Flux.create(sink -> numbers.forEach(sink::next), FluxSink.OverflowStrategy.DROP);

        // then
        StepVerifier.create(actual)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .verifyComplete();
    }

    @Test
    void onBackpressureDrop() {
        Flux.interval(Duration.ofMillis(300))
                .onBackpressureDrop(dropped -> System.out.println("dropped: " + dropped))
                .concatMap(a -> Mono.delay(Duration.ofMillis(1000)).thenReturn(a))
                .doOnNext(a -> System.out.println("Element kept by consumer: " + a))
                .blockLast();
    }

    @Test
    void onBackpressureBuffer() {
        Flux.interval(Duration.ofMillis(10))
                .onBackpressureBuffer(1000, buffered -> System.out.println("buffered: " + buffered))
                .concatMap(a -> Mono.delay(Duration.ofMillis(1000)).thenReturn(a))
                .doOnNext(a -> System.out.println("Element kept by consumer: " + a))
                .blockLast();
    }

    @Test
    void onBackpressureError() {
        Flux.interval(Duration.ofMillis(10))
                .onBackpressureError()
                .concatMap(a -> Mono.delay(Duration.ofMillis(1000)).thenReturn(a))
                .doOnNext(a -> System.out.println("Element kept by consumer: " + a))
                .blockLast();
    }

    @Test
    void onBackpressureLatest() {
        Flux.interval(Duration.ofMillis(10))
                .onBackpressureLatest()
                .concatMap(a -> Mono.delay(Duration.ofMillis(1000)).thenReturn(a))
                .doOnNext(a -> System.out.println("Element kept by consumer: " + a))
                .blockLast();
    }
}
