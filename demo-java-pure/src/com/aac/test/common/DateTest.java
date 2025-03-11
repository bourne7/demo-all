package com.aac.test.common;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Lawrence Peng
 */
public class DateTest {

    public static void main(String[] args) {

        // 打印当前时间
        long now = System.currentTimeMillis();
        System.out.printf("%-30s : %d\n", "now", now);
        System.out.printf("%-30s : %s\n", "new Date()", new Date(now));

        // 多线程安全，可以共享这个
        DateTimeFormatter FORMAT_PATTERN =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        // 将时间戳转换为字符串
        Instant instant = Instant.ofEpochMilli(now);
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.UTC);
        final String nowStr = FORMAT_PATTERN.format(offsetDateTime);
        // 由于使用了 UTC 时间戳，可以看出距离本地真实时间有 8小时 时差
        System.out.printf("%-30s : %s\n", "OffsetDateTime.format", nowStr);

        // 将字符串转换成为时间戳
        now = OffsetDateTime.parse(nowStr, FORMAT_PATTERN).toInstant().toEpochMilli();
        System.out.printf("%-30s : %s\n", "now from nowStr", now);

        /**
         * now                            : 1741078989795
         * new Date()                     : Tue Mar 04 17:03:09 CST 2025
         * OffsetDateTime.format          : 2025-03-04T09:03:09.795Z
         * now from nowStr                : 1741078989795
         */
    }

}
