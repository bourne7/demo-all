package com.example.demo.multiThread.thread;

import java.util.concurrent.TimeUnit;

public class ThreadWorker implements Runnable {
    private volatile boolean flag = true;
    private String threadName;
    int count = 0;

    public ThreadWorker(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        while (flag) {
            System.out.println("Thread: " + threadName + " is running! count = " + count++);

            if (count == 10) {
                flag = false;
            }
            // 这里还要注意， Thread.interrupted() 会清空interrupt 标记。
            // Thread.currentThread().isInterrupted() 这个标记就不会清空。
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("主线程让我停下来，但是我不停。\r\n 当然我也可以在这里设置flag让自己跳出循环，这样看上去就是停下来了。如果要停下来，就设置 flag。");
                flag = false;
            }


            // 注意，sleep 也会清空 interrupt 标识。
            try {
                TimeUnit.MILLISECONDS.sleep(200L);
            } catch (InterruptedException e) {
                System.out.println("线程: " + threadName + " Interrupt");
                e.printStackTrace();
            }
        }
    }
}
