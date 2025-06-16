package com.example.demo.testing;



import com.example.demo.reflect.Car;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListAndSort {

    public static void main(String[] args){
        System.out.println("Hello Mac!");
        // mac commit 1.3

//        List<Car> list = new ArrayList<>();
//        list.add(new Car("Ford",1));
//        list.add(new Car("Ford",2));
//        list.add(new Car("Ford",3));
//        list.add(new Car("Benz",1));
//        list.add(new Car("Benz",2));
//        list.add(new Car("Benz",3));
        String a1 = new String("abc");
        String a2 = "abc";
        String a3 = new String("abc");
        System.out.println(a1.intern()==a2);
        System.out.println(a3==a2);
        System.out.println(a3==a1);


        System.out.println("Sort");
        List<Car> list = new ArrayList<>();

        list.add(new Car("a", 3));
        list.add(new Car("a", 1));
        list.add(new Car("a", 2));

        list.sort(Comparator.comparingInt(Car::getSpeed));

        System.out.println(list);

    }
}
