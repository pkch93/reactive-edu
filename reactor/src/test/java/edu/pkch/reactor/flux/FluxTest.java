package edu.pkch.reactor.flux;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class FluxTest {

    @Test
    void name() {
        Flux<Integer> dataStreams = Flux.just(1, 2, 3);

        dataStreams.flatMap(number -> Mono.just(number * getProductOperand()))
                .subscribe(System.out::println);
    }

    private static int getProductOperand() {
        try {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(1000);
            return 2;
        } catch (InterruptedException e) {
            return 0;
        }
    }

    @Test
    void name2() {
        Flux<Integer> a = Flux.just(1, 2, 3);
        Flux<Integer> b = Flux.just(1, 2, 3);

        a.zipWith(b, (x, y) -> x * y)
                .subscribe(System.out::println);
    }


    @Test
    void name3() {
        Flux<Integer> a = Flux.just(1, 2, 3);
        Flux<Integer> b = Flux.just(1, 2, 3);

        a.publish().refCount(2);

        a.zipWith(b, (x, y) -> x * y)
                .subscribe(System.out::println);
    }
}
