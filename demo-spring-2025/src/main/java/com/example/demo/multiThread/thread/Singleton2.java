package com.example.demo.multiThread.thread;

// 这是最好的单例模式，因为做到了多线程同步以及延迟加载。
public class Singleton2 {
    private Singleton2(){}

    private static class SingletonHolder {
        static final Singleton2 INSTANCE = new Singleton2();
    }

    public static final Singleton2 getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static void main(String[] args) {
        Singleton2 object1 = Singleton2.getInstance();
        Singleton2 object2 = Singleton2.getInstance();
        System.out.println(object1.equals(object2));
    }

}