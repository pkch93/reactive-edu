package edu.pkch.reactor.context;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

class ContextTest {

    @Test
    void contextWrite() {
        Mono<Integer> actual = Mono.just(1)
                .transformDeferredContextual((mono, contextView) -> {
                    Optional<Integer> maybeKey = contextView.getOrEmpty("key");
                    Optional<Integer> maybeKey2 = contextView.getOrEmpty("key2");
                    return mono.map(number -> number + maybeKey.get() + maybeKey2.get());
                })
                .contextWrite(context -> context.put("key2", 20))
                .contextWrite(context -> context.put("key", 10));

        StepVerifier.create(actual)
                .expectNext(31)
                .verifyComplete();
    }

    @Test
    void name() {
        String key = "message";
        Mono<String> actual = Mono.just("Hello")
                .flatMap(s -> Mono.deferContextual(ctx ->
                        Mono.just(s + " " + ctx.get(key))))
                .contextWrite(ctx -> ctx.put(key, "World"));

        StepVerifier.create(actual)
                .expectNext("Hello World")
                .verifyComplete();
    }

    @Test
    void name2() {
        String key = "message";
        Mono<String> actual = Mono.just("Hello")
                .flatMap(s -> Mono.deferContextual(ctx ->
                        Mono.just(s + " " + ctx.get(key))))
                .contextWrite(Context.of(key, "World"));

        StepVerifier.create(actual)
                .expectNext("Hello World")
                .verifyComplete();
    }
}
