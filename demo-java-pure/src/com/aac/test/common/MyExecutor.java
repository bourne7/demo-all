package com.aac.test.common;

import com.aac.test.utils.CommonUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyExecutor {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(() -> {
            CommonUtils.sleepMs(1);
            CommonUtils.print("Hello World");
        });

        try {
            boolean b = executorService.awaitTermination(2, TimeUnit.SECONDS);
            executorService.shutdown();
            // MyUtils.sleep(1);
            CommonUtils.print(executorService.isTerminated());  // 马上执行这句，会是false，除非sleep 一下，或者先执行 isShutdown()
            CommonUtils.print(executorService.isShutdown());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        CommonUtils.print("============");
    }
}
