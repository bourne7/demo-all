package com.example.demo.multiThread.thread;

import java.util.concurrent.TimeUnit;

//https://blog.csdn.net/liuyifeng1920/article/details/53197503
public class TestThreadJoin {

    public static class MyThread extends Thread {
        @Override
        public void run() {

            try {
                int m = (int) (Math.random() * 10);
                System.out.println("我在子线程中会随机睡上0-9秒，时间为=" + m);
                for (; m > 0; m--) {
                    System.out.println("时间为 = " + m);
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("被打断。");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        myThread.start();
        myThread.join();
        System.out.println("正常情况下肯定是我先执行完，但是加入join后，main主线程会等待子线程执行完毕后才执行");
    }
}