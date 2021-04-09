package edu.pkch.reactor.sinks;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

class SinksTest {

    @Test
    void empty() {
        // given
        Sinks.Empty<Object> empty = Sinks.empty();

        // when
        Sinks.EmitResult emitResult = empty.tryEmitEmpty();

        // then
        assertThat(emitResult.isSuccess()).isEqualTo(true);
        assertThat(emitResult.isFailure()).isEqualTo(false);
    }

    @Test
    void one() {
        // given
        Sinks.One<Integer> one = Sinks.one();

        // when
        Sinks.EmitResult emitResult = one.tryEmitValue(1);
        Mono<Integer> actual = one.asMono();

        // then
        assertThat(emitResult.isSuccess()).isEqualTo(true);

        StepVerifier.create(actual)
                .expectNext(1)
                .verifyComplete();
    }

    @Test
    void manyUnicast() {
        // given
        Sinks.Many<Integer> manySink = Sinks.many().unicast().onBackpressureBuffer();

        // when
        manySink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        manySink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);
        manySink.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);
        Flux<Integer> actual = manySink.asFlux();

        // then
        StepVerifier.create(actual)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .verifyComplete();
    }
}
