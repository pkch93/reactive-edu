package edu.pkch.reactivestreams;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.List;

class ReactiveStreamTest {
    @Test
    void reactiveStreams() {
        Publisher<Integer> publisher = ReactiveObservable.create(List.of(1, 2, 3, 4, 5));
        Subscriber<Integer> subscriber = new ReactiveObserver<>();

        publisher.subscribe(subscriber);
    }
}
