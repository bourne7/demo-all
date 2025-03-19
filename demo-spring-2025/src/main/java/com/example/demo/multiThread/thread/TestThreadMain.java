package com.example.demo.multiThread.thread;

import java.util.concurrent.TimeUnit;

public class TestThreadMain {

    public static void main(String[] args) {
//        testInterrupt();
        testSynchronize();
    }

    private static void testSynchronize() {
        // 不同的方式有不同的结果，这个与实现方法有关，这个实验的结论是：synchronize 关键字对于对象的锁定，只是在对象
        // 被访问的时候才会起作用，当这个对象没有在被访问的时候，仍然是不会被阻塞的。
        Account account = new Account();
        Thread t1 = new Thread(account, "线程1");
        Thread t2 = new Thread(account, "线程2");
        t1.start();
        t2.start();
    }

    private static void testInterrupt() {
        Thread thread = new Thread(new ThreadWorker("t"));
        thread.start();
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            System.out.println("主线程Interrupt");
//            e.printStackTrace();
        }
        thread.interrupt();
    }
}
