package commonTest;

public class TestBoxing {
    public static void main(String[] args) {

        Object int1 = Integer.valueOf(2);

        if ((int) int1 > 0) {

            System.out.println(int1);

        }
    }
}
