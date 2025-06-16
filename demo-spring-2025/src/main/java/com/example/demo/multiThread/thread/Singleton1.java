package com.example.demo.multiThread.thread;

public class Singleton1 {
    private Singleton1(){}

    private volatile static Singleton1 instance = null;

    public static Singleton1 getInstance() {
        if (instance == null) {
            synchronized (Singleton1.class) {
                if (instance == null)
                    instance = new Singleton1();
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        //Singleton1.class.newInstance();
    }
}

