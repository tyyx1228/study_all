package com.ty.study;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * java8 newApi日期操作示例
 *
 * @author relax tongyu
 * @create 2018-06-18 11:05
 **/
@Slf4j
public class Java8LocalDateTimeExample {

    /**
     * 使用{@link java.time.format.DateTimeFormatter}解析字符串类型的日期
     */
    @Test
    public void javaTime(){
        String testStr = "2016-12-11 09:37:11.089";

        DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        TemporalAccessor parse = dft.parse(testStr);

        log.info("day of month: {}", parse.get(ChronoField.DAY_OF_MONTH));
        log.info("hour of day: {}", parse.get(ChronoField.HOUR_OF_DAY));
        log.info("milli of second: {}", parse.get(ChronoField.MILLI_OF_SECOND));
    }

    /**
     * 将{@link java.lang.String}转换为{@link java.time.LocalDateTime}
     */
    @Test
    public void javaLocalDateTimeSTR_AS_LOCALDATETIME(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        log.info("use LocalDateTime get current time, and use DateTimeFormatter format as string: {}", dft.format(now));

        String testStr = "2016-12-11T09:37:11";
        LocalDateTime parse = LocalDateTime.parse(testStr);
        log.info("LocalDataTime parse String as Date, the String default format is 'yyyy-MM-ddTHH:mm:ss', {}", dft.format(parse));

        String testStr1 = "2016-12-11 09:37:33";
        LocalDateTime parse1 = LocalDateTime.parse(testStr1, dft);
        log.info("LocalDataTime parse String as Date and use customer format, {}", dft.format(parse1));
    }

    /**
     * 将{@link java.time.LocalDateTime}转换为{@link java.util.Date}
     */
    @Test
    public void javaLocalDateTime_TO_Date(){
        LocalDateTime now = LocalDateTime.now(); // java.time.LocalDateTime

        ZoneId zoneId = ZoneId.systemDefault();  //获取时区
        ZonedDateTime zonedDateTime = now.atZone(zoneId);
        Date date = Date.from(zonedDateTime.toInstant());   // java.util.Date

        log.info("java.time.LocalDateTime --> java.util.Date: {}", date.toString());
    }

    /**
     * 将{@link java.util.Date}转换为{@link java.time.LocalDateTime}
     */
    @Test
    public void javaDate_TO_LocalDateTime(){
        Date date = new Date();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        log.info("java.util.Date --> java.time.LocalDateTime: {}", localDateTime.toString());
    }

}
