package com.example.demo.multiThread.threadWait;


public class Notifier implements Runnable {

    private final Lock lock;

    public Notifier(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + " started");
        try {
            Thread.sleep(30000);
            synchronized (lock) {
                lock.setName("Benz notifier");
//                car.notify();
                lock.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
