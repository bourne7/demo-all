package com.example.demo.reactive.flux;

import org.reactivestreams.Subscriber;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lawrence Peng
 */
public class MyFlux {

    public static void main(String[] args) {

        extracted1();

        extracted2();

    }

    private static void extracted2() {
        List<Integer> elements = new ArrayList<>();

        Flux.just(1, 2, 3, 4)
                .log()
                .subscribe(elements::add);

        System.out.println(elements);
    }

    private static void extracted1() {
        Flux<Integer> flux = Flux.range(1, 10);

        Subscriber<Integer> subscriber = new BaseSubscriber<Integer>() {
            protected void hookOnNext(Integer value) {
                System.out.println(Thread.currentThread().getName() + " -> " + value);

                request(1);
            }

        };

        flux.subscribe(subscriber);
    }

}
