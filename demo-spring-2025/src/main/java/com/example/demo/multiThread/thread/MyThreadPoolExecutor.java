package com.example.demo.multiThread.thread;

import java.lang.reflect.Field;
import java.util.concurrent.*;


public class MyThreadPoolExecutor extends ThreadPoolExecutor {

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);

        Field threadLocals = null;
        try {
            threadLocals = Thread.class.getField("threadLocals");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        threadLocals.setAccessible(true);

        try {
            threadLocals.set(Thread.currentThread(), null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        threadLocals.setAccessible(false);
    }
}
