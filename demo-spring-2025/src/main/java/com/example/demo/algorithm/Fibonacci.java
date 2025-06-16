package com.example.demo.algorithm;

import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Stream.iterate() 产生的流的第一个元素是种子（iterate 方法的第一个参数），然
 * 后将种子传递给方法（iterate 方法的第二个参数）。方法运行的结果被添加到流（作为流
 * 的下一个元素），并被存储起来，作为下次调用 iterate() 方法时的第一个参数，以此类
 * 推。我们可以利用 iterate() 生成一个斐波那契数列
 */
public class Fibonacci {

    // 闭包的一种
    int x = 1;

    Stream<Integer> numbers() {
        return Fibonacci.iterate(0, i -> {
            int result = x + i;
            x = i;
            return result;
        });
    }

    public static void main(String[] args) {
        new Fibonacci().numbers()
                .skip(0) // 过滤前 20 个
                .limit(10) // 然后取 10 个
                .forEach(System.out::println);
    }


    /**
     * 所以这里的 fibonacci 的保存2个数值的地方，分别是调用处保存的 上上一个值，以及 迭代处 保存的上一个值
     * @see Stream#iterate(Object, UnaryOperator)
     */
    public static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f) {

        Objects.requireNonNull(f);

        Spliterator<T> spliterator = new Spliterators.AbstractSpliterator<>(
                Long.MAX_VALUE,
                Spliterator.ORDERED | Spliterator.IMMUTABLE) {
            T prev;
            boolean started;

            @Override
            public boolean tryAdvance(Consumer<? super T> action) {
                Objects.requireNonNull(action);

                /**
                 * 原来的这里的变量我认为没有必要。因为这里没用到 lambda 和 匿名内部类，仅仅是普通方法而已。
                 */
                if (started)
                    prev = f.apply(prev);
                else {
                    // 这里也用了一个 boolean 来进行是否已经开始的标识。
                    prev = seed;
                    started = true;
                }
                action.accept(prev);
                return true;
            }
        };

        return StreamSupport.stream(spliterator, false);
    }

}