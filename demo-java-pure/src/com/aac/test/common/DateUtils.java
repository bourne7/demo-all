package com.aac.test.common;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * All thread safe.
 *
 * @author Lawrence Peng
 */
public class DateUtils {

    public static final ZoneOffset ZONE_OFFSET;

    static {
        Instant instant = Instant.now();
        ZoneId systemZone = ZoneId.systemDefault();
        ZONE_OFFSET = systemZone.getRules().getOffset(instant);
    }

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);

    public static LocalDateTime convertStringToLocalDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, DateUtils.DATE_TIME_FORMATTER);
    }

    public static long convertStringToTimeStamp(String dateTimeString) {
        return convertStringToLocalDateTime(dateTimeString).toInstant(ZONE_OFFSET).toEpochMilli();
    }

}
