package testing;

import reflect.Car;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class CarTest {

    Car car = new Car("Honda", 180);

    @BeforeAll
    static void before() throws Exception {
        System.out.println("BeforeAll");
    }

    @AfterAll
    static void after() throws Exception {
        System.out.println("AfterAll");
    }

    /**
     * Method: getName()
     */
    @Test
    void testGetName() throws Exception {
        String name = car.getName();
        Assertions.assertEquals("Honda", name);
    }

    /**
     * Method: setName(String name)
     */
    @Test
    void testSetName() throws Exception {
        car.setName("Honda2");
    }

    /**
     * Method: getSpeed()
     * 这里就会报错。
     */
    @Test
    void testGetSpeed() throws Exception {
        int speed = car.getSpeed();
        Assertions.assertEquals(180, speed);
    }

    /**
     * Method: setSpeed(int speed)
     */
    @Test
    void testSetSpeed() throws Exception {
        car.setSpeed(99);
    }

    /**
     * Method: info()
     */
    @Test
    void testInfo() throws Exception {
        System.out.println(car.info());
    }


} 
