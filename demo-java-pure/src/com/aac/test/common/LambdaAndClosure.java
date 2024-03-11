package com.aac.test.common;

import java.io.IOException;
import java.util.function.Function;
import java.util.function.IntSupplier;


/**
 * 被 Lambda 表达式引用的局部变量必须是 final 或者是等同 final 效果的。
 */
public class LambdaAndClosure {

    /**
     * 测试 lambda 顺序
     */
    static Function<String, String>
            f1 = s -> {
        System.out.println("f1 " + s);
        return s.replace("MainA", "_");
    },
            f2 = s -> {
                System.out.println("f2 " + s);
                return s.substring(3);
            },
            f3 = s -> {
                System.out.println("f3 " + s);
                return s.toLowerCase();
            },
            f4 = f1.compose(f2).andThen(f3);

    public static void main(String[] args) throws IOException, InterruptedException {

        Closure1 closure1 = new Closure1();
        IntSupplier f1 = closure1.makeFun(0);
        IntSupplier f2 = closure1.makeFun(0);
        IntSupplier f3 = closure1.makeFun(0);
        System.out.println(f1.getAsInt());
        System.out.println(f2.getAsInt());
        System.out.println(f1.getAsInt());
        System.out.println(f3.getAsInt());


//        int a = 0;
////
//        IntStream.range(1, 11).forEach(i -> a += i);

        // ================== 测试顺序

        System.out.println(
                f4.apply("GO AFTER ALL AMBULANCES"));


        // curry
        Function<String, Function<String, String>> sum =
                a -> {
                    return b -> {
                        return a + b;
                    };
                };

        Function<String, String> sumHi =
                sum.apply("-> ");
        System.out.println(sumHi.apply("MainA"));
        System.out.println(sumHi.apply("B"));


    }


    public static class Closure1 {

        /**
         * 那么为什么在 Closure2.java 中，x 和 i 非 final 却可以运行呢？
         * 这就叫做等同 final 效果（Effectively Final）。这个术语是在 Java 8 才开始出现的，
         * 表示虽然没有明确地声明变量是 final 的，但是因变量值没被改变过而实际有了 final
         * 同等的效果。如果局部变量的初始值永远不会改变，那么它实际上就是 final 的。
         * <p>
         * 等同 final 效果意味着可以在变量声明前加上 final 关键字而不用更改任何其余代
         * 码。实际上它就是具备 final 效果的，只是没有明确说明。
         */
        int i = 0;


        /**
         * 让我们回顾一下 Closure1.java。那么现在问题来了：为什么变量 i 被修改编译
         * 器却没有报错呢。它既不是 final 的，也不是等同 final 效果的。因为 i 是外围类
         * 的成员，所以这样做肯定是安全的（除非你正在创建共享可变内存的多个函数）。是
         * 的，你可以辩称在这种情况下不会发生变量捕获（Variable Capture）。但可以肯定的是，
         * Closure3.java 的错误消息是专门针对局部变量的。因此，规则并非只是 “在 Lambda
         * 之外定义的任何变量必须是 final 的或等同 final 效果那么简单。相反，你必须考虑捕
         * 获的变量是否是等同 final 效果的。如果它是对象中的字段，那么它拥有独立的生存周
         * 期，并且不需要任何特殊的捕获，以便稍后在调用 Lambda 时存在。
         * <p>
         * <p>
         * 这里我的理解是需要判断捕获方式是否为 变量捕获
         */
        IntSupplier makeFun(int x) {

            /**
             * 这里可以类比于 匿名内部类：可以共享宿主的所有变量。通过隐式 this 进行？
             */
            return () -> x + i++;
        }
    }

}
