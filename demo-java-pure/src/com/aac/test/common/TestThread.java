package com.aac.test.common;

import com.aac.test.utils.CommonUtils;

public class TestThread {

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            CommonUtils.sleepMs(1);
            System.out.println("Thread is running");
        }
    }

    public static void main(String[] args) {
        Thread myThread = new Thread(new MyRunnable());

        // Using start()
        // myThread.start(); // Starts a new thread, executes run() method concurrently

        // Using run()
        myThread.run(); // Executes run() method in the current thread

        System.out.println("Main thread continues");
    }

}
