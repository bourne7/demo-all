package com.aac.test.common;



import java.util.*;

/**
 * @author pengboran
 */

public class TestGeneric<T> {

    public Map<String, T> map = new HashMap<>();


    public static class A<T, ID> {
    }

    public static class B extends A<String, Integer> {
    }

    public static void main(String[] args) {

        TestGeneric<String> testGeneric = new TestGeneric<>();


        Map<String, String> map = testGeneric.map;


        Map<String, Date> map1 = new HashMap<>();

        print(map1);

        Map<String, Date> map2 = new HashMap<String, Date>();

        print(map2);

        Map<String, Date> map3 = new HashMap<String, Date>() {
        };

        print(map3);


//        unmatchedType();


        B b = new B();
        print(b);
    }

    // https://blog.csdn.net/qq_33217349/article/details/89178683
    // https://www.iteye.com/blog/rednaxelafx-586212
    private static void unmatchedType() {
        List<Integer> li = new ArrayList<>();
        li.add(6);
        li.add(5);
        List list = li;
        List<String> ls = list;
//        一样的道理，List没有泛型信息，所以li的泛型信息就丢失了，所以赋值给ls
//        是没有问题的

        System.out.println(ls.get(0));
//        但是当访问ls中的元素的时候，就会发生类型不匹配的问题
    }

    private static void print(Object map) {
//        Type[] actualTypeArguments = ((ParameterizedTypeImpl) map.getClass().getGenericSuperclass()).getActualTypeArguments();
//
//        Stream.of(actualTypeArguments).forEach(v -> System.out.println(v.getTypeName()));
    }
}
