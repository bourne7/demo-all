package com.example.demo.multiThread.thread;

public class Account implements Runnable {
    // 这里要注意，++是会new 一个新的对象的，所以在锁对象的时候，每次都会改变。
    public Integer object = 0;

    public Integer count = 0;

//    public AtomicInteger count = new AtomicInteger(0);

    private byte[] lock = new byte[0];

    public void add(int addCount) {
        synchronized (this) {
            for (int i = 1; i <= addCount; i++) {
                count++;
//                object++;
                //System.out.println(Thread.currentThread().getName() + ": " + count);
//                if (Thread.holdsLock(count)) {
//                    System.out.println("Flag: " + count);
//                }
            }
//            System.out.println("object: " + object);
        }

    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " 开始。");
        add(1000000);
        System.out.println(Thread.currentThread().getName() + " 结束。count = " + count);
    }


}
