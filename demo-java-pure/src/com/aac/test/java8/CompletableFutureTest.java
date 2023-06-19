package com.aac.test.java8;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureTest {

    public static void main(String[] args) {

        CompletionStage<Integer> currentStage = CompletableFuture.completedFuture(0);

        currentStage = currentStage.thenCompose(p -> {
            System.out.println(Thread.currentThread().getName() + " " + p);
            return CompletableFuture.completedFuture(p + 1);
        });

        currentStage = currentStage.thenCompose(p -> {
            System.out.println(Thread.currentThread().getName() + " exception " + p);

            if (p < 999) {
                throw new RuntimeException("[" + p + "]");
            }

            return CompletableFuture.completedFuture(p + 1);
        });

        currentStage = currentStage.thenCompose(p -> {
            System.out.println(Thread.currentThread().getName() + " " + p);
            return CompletableFuture.completedFuture(p + 1);
        }).handle((result, throwable) -> {
            if (throwable != null) {
//                throwable.printStackTrace();
            }
            return -1;
        });

        System.out.println("=================== 1");

        currentStage = currentStage.thenApply(p -> p >= 3 ? 100 : 200);

        System.out.println("=================== 2");
        currentStage = currentStage.thenApply(p -> p == 100 ? p + 1 : p);
        currentStage = currentStage.thenApply(p -> p == 200 ? p + 2 : p);

        System.out.println("Final result: " + currentStage.toCompletableFuture().join());

    }

}
