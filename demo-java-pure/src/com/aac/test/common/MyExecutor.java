package com.aac.test.common;

import com.aac.test.utils.MyUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyExecutor {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(() -> {
            MyUtils.sleep(1);
            MyUtils.print("Hello World");
        });

        try {
            boolean b = executorService.awaitTermination(2, TimeUnit.SECONDS);
            executorService.shutdown();
            // MyUtils.sleep(1);
            MyUtils.print(executorService.isTerminated());  // 马上执行这句，会是false，除非sleep 一下，或者先执行 isShutdown()
            MyUtils.print(executorService.isShutdown());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        MyUtils.print("============");
    }
}
