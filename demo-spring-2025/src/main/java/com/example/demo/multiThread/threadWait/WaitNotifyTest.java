package com.example.demo.multiThread.threadWait;


public class WaitNotifyTest {

    public static void main(String[] args) {

        Lock car = new Lock("Benz");

        Waiter waiter1 = new Waiter(car);
        new Thread(waiter1, "thread-waiter1").start();

        Waiter waiter2 = new Waiter(car);
        new Thread(waiter2, "thread-waiter2").start();

        Notifier notifier = new Notifier(car);
        new Thread(notifier, "thread-notifier").start();

        System.out.println("All the threads are started");
    }


}
