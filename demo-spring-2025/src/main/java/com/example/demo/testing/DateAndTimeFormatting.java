package com.example.demo.testing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;

public class DateAndTimeFormatting {
    /**
     * 个人觉得最好直接用 LocalDateTime 进行所有操作以后再转成需要的格式。
     */
    public static void main(String[] args) throws Exception {
//        print("测试 LocalDate");
//        localDateTest();
//
//        print("测试 LocalTime");
//        localTimeTest();
//
//        print("测试 LocalDateTime");
//        localDateTimeTest();
//
        print("测试 格式化");
        localDateTimeFormat();
//
//        print("测试 Date 转换为 LocalDateTime");
//        date2LocalDateTime();
//
//        print("测试 LocalDateTime 转换为 Date");
//        localDate2DateTime();
//
//        print("测试 比较时间");
//        compareTime();
//
//        print("测试 ISO 8601 转化");
//        convert_ISO_8691();
//
//        print("测试 时间加减");
//        timeCalc();

    }

    private static void print(String content) {
        System.out.println(String.format(System.lineSeparator() + "========= %s ===========", content));
    }

    private static void timeCalc() {
        // Period表示以年、月、日的长度。
        Period period = Period.ofDays(1);
        // Duration表示以秒和纳秒。
        Duration duration = Duration.ofHours(1);

        LocalDateTime now = LocalDateTime.now();

        System.out.println("日期的相加，要用LocalDate：" + now.toLocalDate().plus(period));
        System.out.println("时间的相加，要用LocalTime：" + now.toLocalTime().plus(duration));
        System.out.println("直接加 period 和 duration ，这个比较好用：" + now.plus(duration).plus(period));
        System.out.println("直接加数字，这个最好用：" + now.plusDays(1).plusHours(1));

    }

    private static void convert_ISO_8691() {
        LocalDateTime now = LocalDateTime.now();
        ZoneOffset zoneOffset = ZoneOffset.of("+8");
        ZonedDateTime zonedDateTime = now.atZone(zoneOffset); //you might use a different zone
        String iso8601 = zonedDateTime.toString();
        System.out.println("iso8601 time: " + zonedDateTime);
        System.out.println("iso8601 time: " + iso8601);
        ZonedDateTime zdt = ZonedDateTime.parse(iso8601);
        LocalDateTime ldt = zdt.toLocalDateTime();
    }

    private static void localDateTimeFormat() throws ParseException {
        LocalDateTime now = LocalDateTime.now();

        String dateStringShort = "20180101";
        DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
        LocalDate formatted = LocalDate.parse(dateStringShort, formatter);
        System.out.println(formatted);

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("YYYY MM dd");
        System.out.println(now.format(formatter2));
        System.out.println(formatter2.format(now));

        /**
         * 线程安全
         */
        DateTimeFormatter STANDARD_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTimeStringNow = STANDARD_FORMATTER.format(now);
        System.out.println(formatDateTimeStringNow);

//        String dataStringLong = "2023-01-01 00:00:00";
        String dataStringLong = formatDateTimeStringNow;

        System.out.println(dataStringLong);
        LocalDateTime localDateTime = LocalDateTime.parse(dataStringLong, STANDARD_FORMATTER);

        /**
         * 做成全局共享的
         */
        String zoneId = TimeZone.getDefault().toZoneId().toString();

        /**
         * 全局共享的，因为是线程安全的
         */
        ZoneOffset zoneOffset = ZoneOffset.of(zoneId);

        System.out.println(localDateTime.toInstant(zoneOffset).toEpochMilli());


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormat.parse(dataStringLong);
        System.out.println(date.getTime());
        System.out.println(System.currentTimeMillis());
    }

    private static void compareTime() {
        LocalDate today = LocalDate.now();
        LocalDate specifyDate = LocalDate.of(2015, 10, 2);
        Period period = Period.between(specifyDate, today);

        System.out.println("相隔年： " + period.getYears());
        System.out.println("相隔月： " + period.getMonths());
        System.out.println("相隔天数： " + period.getDays());
        System.out.println("相隔总天数： " + specifyDate.until(today, ChronoUnit.DAYS));
        System.out.println("比较时间 today.isAfter(specifyDate)： " + today.isAfter(specifyDate));
    }

    private static void date2LocalDateTime() {
        Date date = new Date();
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        System.out.println("Date = " + date);
        System.out.println("LocalDateTime = " + localDateTime);
    }

    private static void localDate2DateTime() {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zdt = now.atZone(zoneId);

        Date date = Date.from(zdt.toInstant());

        System.out.println("LocalDateTime = " + now);
        System.out.println("Date = " + date);
    }

    private static void localDateTimeTest() {

        //查看美国纽约当前的时间
        System.out.println("查看当前的时区 ZoneId: " + ZoneId.systemDefault()); //Asia/Shanghai
        ZoneId america = ZoneId.of("America/New_York");
        LocalDateTime shanghaiTime = LocalDateTime.now();
        LocalDateTime americaDateTime = LocalDateTime.now(america);
        System.out.println("shanghaiTime" + shanghaiTime); //2016-11-06T15:20:27.996
        System.out.println("americaDateTime" + americaDateTime); //2016-11-06T02:20:27.996 ，可以看到美国与北京时间差了13小时

        //带有时区的时间
        ZonedDateTime americaZoneDateTime = ZonedDateTime.now(america);
        System.out.println("带有时区的时间" + americaZoneDateTime); //2016-11-06T02:23:44.863-05:00[America/New_York]

        //Creating LocalDateTime by providing input arguments
        LocalDateTime specificDate = LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 30);
        System.out.println("Specific Date= " + specificDate);

        //Current date in "Asia/Kolkata", you can get it from ZoneId javadoc
        LocalDateTime todayKolkata = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        System.out.println("Current Date in IST= " + todayKolkata);

        // 最好使用这种，比较直观的时区
        LocalDateTime hereTime = LocalDateTime.now(ZoneId.of("UTC+8"));
        System.out.println("Current Date in UTC= " + hereTime);

        //java.time.zone.ZoneRulesException: Unknown time-zone ID: IST
        //LocalDateTime todayIST = LocalDateTime.now(ZoneId.of("IST"));

        //Getting date from the base date i.e 01/01/1970
        LocalDateTime dateFromBase = LocalDateTime.ofEpochSecond(3600, 0, ZoneOffset.UTC);
        System.out.println("3600 th second time from 01/01/1970= " + dateFromBase);

        // 同样，这种方式也比较科学。
        ZoneOffset zoneOffset = ZoneOffset.of("+8");
        LocalDateTime dateFromBase2 = LocalDateTime.ofEpochSecond(3600, 0, zoneOffset);
        System.out.println("3600 th second time from 01/01/1970 2= " + dateFromBase2);
    }

    private static void localTimeTest() {
        //Current Time
        LocalTime time = LocalTime.now();
        System.out.println("Current Time=" + time);

        //Creating LocalTime by providing input arguments
        LocalTime specificTime = LocalTime.of(12, 20, 25, 40);
        System.out.println("Specific Time of Day=" + specificTime);

        //Try creating time by providing invalid inputs
        //LocalTime invalidTime = LocalTime.of(25,20);
        //Exception in thread "main" java.time.DateTimeException:
        //Invalid value for HourOfDay (valid values 0 - 23): 25

        //Current date in "Asia/Kolkata", you can get it from ZoneId javadoc
        LocalTime timeKolkata = LocalTime.now(ZoneId.of("Asia/Kolkata"));
        System.out.println("Current Time in IST=" + timeKolkata);

        //java.time.zone.ZoneRulesException: Unknown time-zone ID: IST
        //LocalTime todayIST = LocalTime.now(ZoneId.of("IST"));

        //Getting date from the base date i.e 01/01/1970
        LocalTime specificSecondTime = LocalTime.ofSecondOfDay(10000);
        System.out.println("10000th second time= " + specificSecondTime);
    }

    private static void localDateTest() {
        //Current Date
        LocalDate today = LocalDate.now();
        System.out.println("Current Date= " + today);

        //Creating LocalDate by providing input arguments
        LocalDate firstDay_2014 = LocalDate.of(2014, Month.JANUARY, 1);
        System.out.println("Specific Date= " + firstDay_2014);

        //Try creating date by providing invalid inputs
        //LocalDate feb29_2014 = LocalDate.of(2014, Month.FEBRUARY, 29);
        //Exception in thread "main" java.time.DateTimeException:
        //Invalid date 'February 29' as '2014' is not a leap year

        //Current date in "Asia/Kolkata", you can get it from ZoneId javadoc
        LocalDate todayKolkata = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        System.out.println("Current Date in IST= " + todayKolkata);

        //java.time.zone.ZoneRulesException: Unknown time-zone ID: IST

        //Getting date from the base date i.e 01/01/1970
        LocalDate dateFromBase = LocalDate.ofEpochDay(365);
        System.out.println("365th day from base date= " + dateFromBase);

        LocalDate hundredDay2014 = LocalDate.ofYearDay(2014, 100);
        System.out.println("100th day of 2014= " + hundredDay2014);
    }
}
