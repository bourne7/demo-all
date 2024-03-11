package com.aac.test.common;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Lawrence Peng
 */
public class DateTest {

    public static void main(String[] args) {
        System.out.println(System.lineSeparator() + "====================================" + System.lineSeparator());
        System.out.println("System.currentTimeMillis(): " + System.currentTimeMillis());
        System.out.println(System.lineSeparator() + "====================================" + System.lineSeparator());

        /**
         * 如何产生一个 UTC 时间
         */
        LocalDateTime localDateTime;
        // 这里选择方法1
        localDateTime = LocalDateTime.now();
        localDateTime = method1();
//        localDateTime = method2();

        // 先产生一个 LocalDateTime，然后再转换成 UTC 时间。
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
        long epochMilli_SHANGHAI = zonedDateTime.toInstant().toEpochMilli();
        System.out.println("zonedDateTime: " + zonedDateTime + " epochMilli_SHANGHAI: " + epochMilli_SHANGHAI);

        zonedDateTime = localDateTime.atZone(ZoneId.of("UTC"));
        long epochMilli_UTC = zonedDateTime.toInstant().toEpochMilli();
        System.out.println("zonedDateTime: " + zonedDateTime + " epochMilli_UTC: " + epochMilli_UTC);

        System.out.println("字面时间一样，时区不一样的时候，epochMilli_SHANGHAI - epochMilli_UTC = " + (epochMilli_SHANGHAI - epochMilli_UTC));

        System.out.println(System.lineSeparator() + "====================================" + System.lineSeparator());
    }

    /**
     * 方法1： Define a date and time without a time zone
     */
    private static LocalDateTime method1() {
        LocalDateTime localDateTime = LocalDateTime.of(2018, 6, 26, 0, 0, 0);
        System.out.println("method1: LocalDateTime.of...: " + localDateTime);
        return localDateTime;
    }

    /**
     * 方法2： 用这个方法可以返回一个 UTC 的钟，用于产生 LocalDateTime
     */
    private static LocalDateTime method2() {

        Clock clock = Clock.systemUTC();
        System.out.println("Clock.systemUTC().millis(): " + clock.millis());

        clock = Clock.systemDefaultZone();
        System.out.println("Clock.systemDefaultZone().millis(): " + clock.millis());

        LocalDateTime localDateTime = LocalDateTime.now(clock);
        System.out.println("method2: LocalDateTime.of(Clock): " + localDateTime);
        return localDateTime;
    }

}
