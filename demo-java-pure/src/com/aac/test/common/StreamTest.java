package com.aac.test.common;

import java.util.regex.Pattern;
import java.util.stream.Stream;


public class StreamTest {


    public static void main(String[] args) {

        StreamTest test = new StreamTest();

        // 如果这里先提取出来，则不能重复使用这个流了。
        Stream<String> stream = test.stream();

        test.stream()
                .limit(7)
                .map(w -> w + " ")
                .forEach(System.out::print);


        test.stream()
                .skip(7)
                .limit(2)
                .map(w -> w + " ")
                .forEach(System.out::print);

    }

    public Stream<String> stream() {
        return Pattern.compile("[ .,?]+").splitAsStream("Not much of a cheese shop really is it");
    }


}
