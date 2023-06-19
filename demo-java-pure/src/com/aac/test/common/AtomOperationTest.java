package com.aac.test.common;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomOperationTest {
    public static void main(String[] args) throws Exception {
        System.out.println(Thread.currentThread().getName() + " - " + Wrapper.staticInstance.x + " - " + Wrapper.staticInstance);

        AtomicInteger int1 = new AtomicInteger(Integer.MIN_VALUE);
        AtomicInteger int2 = new AtomicInteger(Integer.MAX_VALUE);

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            new Thread("Thread 1") {
                @Override
                public void run() {
//                sleep2(100L);
                    Wrapper.staticInstance = new Point(int1.getAndIncrement());

                    if (Wrapper.staticInstance.x > 0) {
                        System.out.println(Thread.currentThread().getName() + " - " + Wrapper.staticInstance.x + " - " + Wrapper.staticInstance);
                    }
                }
            }.start();

            new Thread("Thread 2") {
                @Override
                public void run() {

                    Wrapper.staticInstance = new Point(int2.getAndDecrement());

                    if (Wrapper.staticInstance.x < 0) {
                        System.out.println(Thread.currentThread().getName() + " - " + Wrapper.staticInstance.x + " - " + Wrapper.staticInstance);
                    }
                    
                }
            }.start();
        }
        System.out.println("=====");
    }

    private static void sleep2(long time) {
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (Exception e) {
        }
    }

    public static class Point {
        public Point() {
        }
        public Point(int x) {
            this.x = x;
        }
        public int x;
    }

    public static class Wrapper {
        public static Point staticInstance = new Point();
    }
}