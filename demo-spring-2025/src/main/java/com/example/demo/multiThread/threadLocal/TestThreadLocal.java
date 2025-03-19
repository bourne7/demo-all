package com.example.demo.multiThread.threadLocal;

public class TestThreadLocal {

    ThreadLocal<Long> longLocal = new ThreadLocal<>();
    ThreadLocal<String> stringLocal = new ThreadLocal<>();

    public void set() {
        longLocal.set(Thread.currentThread().getId());
        stringLocal.set(Thread.currentThread().getName());
    }

    public long getLong() {
        return longLocal.get();
    }

    public String getString() {
        return stringLocal.get();
    }

    public static void main(String[] args) throws InterruptedException {
        final TestThreadLocal threadLocalTest = new TestThreadLocal();

        threadLocalTest.set();
        System.out.println(threadLocalTest.getLong());
        System.out.println(threadLocalTest.getString());

        Thread thread1 = new Thread(() -> {
            threadLocalTest.set();
            System.out.println(threadLocalTest.getLong());
            System.out.println(threadLocalTest.getString());
        });

        thread1.start();
        thread1.join();

        System.out.println(threadLocalTest.getLong());
        System.out.println(threadLocalTest.getString());
    }
}