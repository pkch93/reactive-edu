package edu.pkch.reactor.mono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.function.Function;

class CombineTest {

    @Test
    void merge() {
        // given
        Mono<Integer> a = Mono.just(1);
        Mono<Integer> b = Mono.just(2);
        Mono<Integer> c = Mono.just(3);

        // when
        Flux<Integer> actual = Flux.merge(a, b, c);

        // then
        StepVerifier.create(actual)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .verifyComplete();
    }

    @Test
    void merge2() {
        Function<Integer, Integer> powerNumber = number -> {
            return number * number;
        };
        Mono<Integer> byInteger = Mono.defer(() -> Mono.just(powerNumber.apply(1)));

        Mono<Integer> actual = byInteger.mergeWith(byInteger).mergeWith(byInteger)
                .flatMap(Mono::just)
                .reduce(Integer::sum)
                .subscribeOn(Schedulers.single());

        StepVerifier.create(actual)
                .expectNext(3)
                .verifyComplete();
    }

    @Test
    void zip() {
        // given
        Mono<Integer> a = Mono.just(1);
        Mono<Integer> b = Mono.just(2);
        Mono<Integer> c = Mono.just(3);

        // when
        Mono<Integer> actual = Mono.zip(data -> Arrays.stream(data)
                .mapToInt(obj -> (int) obj)
                .sum(), a, b, c);

        // then
        StepVerifier.create(actual)
                .expectNext(6)
                .verifyComplete();
    }

    @Test
    void expand() {
        // given
        Mono<Integer> a = Mono.just(1);

        // when
        Flux<Integer> actual = a.expand(data -> data > 30 ? Mono.empty() : Mono.just(data * 2));

        // then
        StepVerifier.create(actual)
                .expectNext(1)
                .expectNext(2)
                .expectNext(4)
                .expectNext(8)
                .expectNext(16)
                .expectNext(32)
                .verifyComplete();
    }

    @Test
    void expand_and_reduce() {
        // given
        Mono<Integer> a = Mono.just(1);

        // when
        Mono<Integer> actual = a.expand(data -> data > 10 ? Mono.empty() : Mono.just(data * 2))
                .reduce(Integer::sum);

        // then
        StepVerifier.create(actual)
                .expectNext(31)
                .verifyComplete();
    }
}
