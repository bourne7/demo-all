package com.example.demo.multiThread.threadPool;

import java.util.concurrent.TimeUnit;

public class MyTask implements Runnable {
    private int taskNum;

    public MyTask(int num) {
        this.taskNum = num;
    }

    @Override
    public void run() {
        System.out.println("进入 MyTask " + taskNum);
        try {
            int waitTime = 3;
            TimeUnit.SECONDS.sleep(waitTime);
            System.out.println("等待了 " + waitTime + " 秒以后。MyTask " + taskNum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
