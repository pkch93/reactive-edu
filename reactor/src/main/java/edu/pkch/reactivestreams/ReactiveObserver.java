package edu.pkch.reactivestreams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class ReactiveObserver<T> implements Subscriber<T> {

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T t) {
        System.out.println("subscribe: " + t);
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("error: " + t.getClass().getName());
    }

    @Override
    public void onComplete() {
        System.out.println("data stream completed");
    }
}
