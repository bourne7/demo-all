package com.aac.test.java21;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;


public class VirtualThread {

    public static void main(String[] args) {
        execute(Executors.newVirtualThreadPerTaskExecutor());
        execute(Executors.newFixedThreadPool(500));
    }

    private static void execute(ExecutorService executor) {
        long start = System.currentTimeMillis();
        try (executor) {
            IntStream.range(0, 4_000).forEach(i -> executor.submit(() -> {
                Thread.sleep(Duration.ofSeconds(1));
                // System.out.println(i);
                return i;
            }));
        }
        long time = System.currentTimeMillis() - start;
        System.out.println(executor.getClass() + " use: " + time / 1000 + " seconds");
    }
}
