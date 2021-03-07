package edu.pkch.reactivestreams;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

public class ReactiveObservable<T> implements Publisher<T> {
    private final List<T> data;

    private ReactiveObservable(List<T> data) {
        this.data = data;
    }

    public static <T> ReactiveObservable<T> create(List<T> data) {
        return new ReactiveObservable<>(data);
    }

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        subscriber.onSubscribe(new Subscription() {
            private boolean cancelled;

            @Override
            public void request(long n) {
                try {
                    for(long i = 0; i < n; i++) {
                        if (cancelled) {
                            return;
                        }

                        if (data.size() <= i) {
                            break;
                        }

                        subscriber.onNext(data.get((int) i));
                    }

                    subscriber.onComplete();
                } catch (Throwable t) {
                    subscriber.onError(t);
                }
            }

            @Override
            public void cancel() {
                this.cancelled = true;
            }
        });
    }
}
