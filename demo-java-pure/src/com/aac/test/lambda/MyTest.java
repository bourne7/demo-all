package com.aac.test.lambda;

import java.util.Arrays;

/**
 * https://zhuanlan.zhihu.com/p/27159693
 *
 *
 * javac -g MyTest.java
 *
 * javap -verbose -private MyTest
 */
public class MyTest {

    public static void main(String[] args) {
        Runnable r = () -> System.out.println(Arrays.toString(args));
        r.run();
    }

}