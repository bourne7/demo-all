package com.example.demo.testing;

import org.apache.commons.lang3.StringUtils;

public class Number {

    public static void main(String[] args) {
        System.out.println(0xffffffff);
        System.out.println(Integer.toBinaryString(0xffffffff));

        byte b = -1;
        System.out.println((int) (char) b);
        System.out.println((int) (char) (b & 0xff));

        System.out.println(Number.class);
        System.out.println(Number.class.getSimpleName());

        System.out.println(Byte.valueOf("-1"));

        int BIT_SEQUENCE = 2;
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.toBinaryString(-1 << 1));
        System.out.println(-1L ^ (-1L << BIT_SEQUENCE));
        System.out.println(~(-1L << BIT_SEQUENCE));

//        private final static long MAX_SEQUENCE = -1L ^ (-1L << BIT_SEQUENCE);
//        private final static long MAX_SEQUENCE= ~(-1L << BIT_SEQUENCE);

    }

    private void testLong() {
        long l = 1L;

        int i = 2;

        l |= i;

        String format = StringUtils.leftPad(Long.toBinaryString(l), Long.SIZE, "0");

        System.out.println(format);
    }


}
