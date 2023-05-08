package commonTest;

import reflect.Car;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author pengboran
 * @since 2021-02-18 18:04
 */
public class TestListSort {

    public static void main(String[] args) {


        List<Car> list = new ArrayList<>();

        list.add(new Car("a", 3));
        list.add(new Car("a", 1));
        list.add(new Car("a", 2));

        list.sort(Comparator.comparingInt(Car::getSpeed));

        System.out.println(list);

    }
}
