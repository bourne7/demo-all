package com.example.demo.multiThread.threadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThreadPool {
    public static void main(String[] args) throws Exception {
        // 注意，这里如果使用ExecutorService接口的话，会导致返回的对象失去部分方法，所以最好还是强制转化一下，这里的
        // 前置转化肯定是不会报错的。
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5));

        for (int i = 0; i < 10; i++) {
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
            System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，等待中的任务数目：" +
                    executor.getQueue().size() + "，已执行完的任务数目：" + executor.getCompletedTaskCount());
        }

        for (int i = 0; i < 20; i++) {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("监控中：线程池中线程数目：" + executor.getPoolSize() + "，等待中的任务数目：" +
                    executor.getQueue().size() + "，已执行完的任务数目：" + executor.getCompletedTaskCount());
        }

//        executor.shutdown();
    }

}
