package com.aac.test.common;

/**
 * one should use t1 - t0 < 0, not t1 < t0, because of the possibility of numerical overflow.
 *
 * https://stackoverflow.com/questions/18414059/why-should-i-use-t1-t0-0-not-t1-t0-when-using-system-nanotime-in-java/18414157#18414157
 *
 * use t1 - t0 < 0 here because the base time could be very large, and we must make sure that future is larger than past.
 *
 * Normally, we should use < to compare two longs
 *
 */
public class NanoTimeCompareLong {

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        System.out.println(System.nanoTime());

    }

}
