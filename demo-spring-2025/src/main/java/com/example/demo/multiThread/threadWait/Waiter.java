package com.example.demo.multiThread.threadWait;


public class Waiter implements Runnable {

    private final Lock lock;

    public Waiter(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        // 这里的 wait 会释放当前资源。
        // 如果这里不加同步锁的话，大概率会有并发问题，比如 notify 的消息会收不到。
        synchronized (lock) {
            try {
                System.out.println(name + " waiting to get notified at time:" + System.currentTimeMillis());
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " waiter thread got notified at time:" + System.currentTimeMillis());
            //process the message now
            System.out.println(name + " processed: " + lock.getName());
        }
    }

}
