package com.aac.test.utils;

import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * @author Lawrence Peng
 */
public class MyUtils {

    public static void sleep(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void print(Object... obj) {
        Stream.of(obj).forEach(v -> System.out.println(Thread.currentThread().getName() +
                " | isDaemon: " +
                Thread.currentThread().isDaemon() +
                " | " +
                v)
        );
    }

    public static Throwable extractRealException(Throwable throwable) {
        //这里判断异常类型是否为CompletionException、ExecutionException，如果是则进行提取，否则直接返回。
        if (throwable instanceof CompletionException || throwable instanceof ExecutionException) {
            if (throwable.getCause() != null) {
                return throwable.getCause();
            }
        }
        return throwable;
    }

}
