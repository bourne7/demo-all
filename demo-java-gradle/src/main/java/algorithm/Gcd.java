package algorithm;

import org.apache.commons.math3.util.ArithmeticUtils;

public class Gcd {

    public static void main(String[] args) throws Exception {
        int gcd = ArithmeticUtils.gcd(1000, 840);

        System.out.println(gcd);
    }

}
