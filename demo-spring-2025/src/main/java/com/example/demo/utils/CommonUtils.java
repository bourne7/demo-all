package com.example.demo.utils;

import java.util.concurrent.TimeUnit;


public class CommonUtils {

    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
