package com.example.demo.testing;

/**
 * @author Lawrence Peng
 */
public class A {

    private static A a = new A();
    // 初始化的时候会忽视已经有了值的。
    public static int num1;
    public static int num2 = 0;

    public A() {
        num1++;
        num2++;
        System.out.println("-num1:" + num1);
        System.out.println("-num2:" + num2);
    }

    public static A getInstance() {
        return a;
    }

    public static void staticMethod() {
        System.out.println("staticMethod");
    }

    public static void main(String[] args) {

        // 执行静态方法前，会加载类。
        A.getInstance();
        System.out.println("num1:" + A.num1);
        System.out.println("num2:" + A.num2);

        /*
        -num1:1
        -num2:1
        num1:1
        num2:0
        */

    }
}
