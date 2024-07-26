package com.aac.test.utils;

import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * @author Lawrence Peng
 */
public class CommonUtils {

    public static void sleep(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void print(Object... objects) {
        String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();

        String prefix = Thread.currentThread().getName() + " | "
                + className + "." + methodName + "():" + lineNumber + "\t|";

        Stream.of(objects).forEach(v -> System.out.println(prefix + v.toString()));
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
