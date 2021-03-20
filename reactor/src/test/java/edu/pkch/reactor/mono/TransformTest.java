package edu.pkch.reactor.mono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class TransformTest {

    @Test
    void switchIfEmpty() {
        Mono<Integer> actual = Mono.<Integer>empty()
                .switchIfEmpty(Mono.just(10));
        
        StepVerifier.create(actual)
                .expectNext(10)
                .verifyComplete();
    }

    @Test
    void defaultIfEmpty() {
        Mono<Integer> actual = Mono.<Integer>empty()
                .defaultIfEmpty(10);

        StepVerifier.create(actual)
                .expectNext(10)
                .verifyComplete();
    }
}
