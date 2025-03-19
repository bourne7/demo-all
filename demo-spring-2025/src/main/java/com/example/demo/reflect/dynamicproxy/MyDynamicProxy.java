package com.example.demo.reflect.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;


public class MyDynamicProxy {

    public static void main(String[] args) {

        InvocationHandler handler = (proxy, method, arguments) -> {
            System.out.println(method);

            Object returnObject = null;

            switch (method.getName()) {
                case "morning":
                    System.out.println("Good morning, " + arguments[0]);
                    break;
                case "hello":
                    String str = "Hello, " + arguments[0];
                    System.out.println(str);
                    returnObject = str;
                default:
                    break;
            }

            return returnObject;
        };

        Hello hello = (Hello) Proxy.newProxyInstance(
                // 传入ClassLoader
                Hello.class.getClassLoader(),

                // 传入要实现的接口
                new Class[]{Hello.class},

                // 传入处理调用方法的InvocationHandler
                handler
        );

        hello.morning("Bob");
        hello.hello("a");
    }
}

interface Hello {

    void morning(String name);

    String hello(String name);

}