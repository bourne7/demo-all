package com.example.demo.multiThread.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * https://www.jianshu.com/p/4ed887664b13
 */
public class TestAtomicInteger {

    private static final int THREADS_COUNT = 2;

    public static int count = 0;
    public static volatile int countVolatile = 0;
    public static AtomicInteger atomicInteger = new AtomicInteger(0);
    public static CountDownLatch countDownLatch = new CountDownLatch(THREADS_COUNT);

    public static void increase() {
        count++;
        countVolatile++;
        atomicInteger.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                IntStream.range(0, 1000).forEach(i1 -> increase());
                countDownLatch.countDown();
            });
            threads[i].start();
        }

        countDownLatch.await();

        System.out.println(count);
        System.out.println(countVolatile);
        System.out.println(atomicInteger.get());
    }
}