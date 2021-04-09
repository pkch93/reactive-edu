package edu.pkch.reactor.hot;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import static reactor.core.publisher.Sinks.EmitFailureHandler.*;

class HotStreamTest {

    @Test
    void hot() {
        // given
        Sinks.Many<Integer> hotSource = Sinks.unsafe().many().multicast().directBestEffort();
        Flux<Integer> hotFlux = hotSource.asFlux();

        hotFlux.subscribe(d -> System.out.println("Subscriber 1 to Hot Source: " + d));

        hotSource.emitNext(1, FAIL_FAST);
        hotSource.tryEmitNext(2).orThrow();

        hotFlux.subscribe(d -> System.out.println("Subscriber 2 to Hot Source: " + d));

        hotSource.emitNext(3, FAIL_FAST);
        hotSource.emitNext(4, FAIL_FAST);
        hotSource.emitComplete(FAIL_FAST);
    }
}
