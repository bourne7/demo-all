package com.aac.test.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

import java.util.Date;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author Lawrence Peng
 */
public class CommonUtils {

    public static void sleepMs(int time) {
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void print(Object... objects) {
        String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();

        String prefix = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MS_FORMATTER) + " | "
                + Thread.currentThread().getName() + " | "
                + className + "." + methodName + "():" + lineNumber + "\t|";

        if (objects != null && objects.length == 1) {
            System.out.println(prefix + objects[0]);
        } else if (objects != null && objects.length > 1) {
            System.out.println(prefix + objects[0] + " | " + objects.length);
        } else {
            System.out.println(prefix + " | null");
        }
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
