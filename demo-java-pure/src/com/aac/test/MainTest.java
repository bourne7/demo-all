package com.aac.test;

import java.util.HashMap;
import java.util.Map;

public class MainTest {

    public static void main(String[] args) throws Exception {

        String str1 = "Hello World!";
        String str2 = "Hello World!";
        String str3 = new String("Hello World!");

        System.out.println(str1 == str2);
        System.out.println(str1 == str3);
        System.out.println(str1.hashCode());
        System.out.println(str2.hashCode());
        System.out.println(str3.hashCode());


        Map<String, Object> map = new HashMap<>();

        map.put("1", "1");

        Object o = map.get("1");



    }
}
