package com.ty.study;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

/**
 * Joda日期操作示列
 *
 * @author relax tongyu
 * @create 2018-06-18 13:29
 **/
@Slf4j
public class JodaDateTimeExample {

    @Test
    public void date_timezone(){
        String testStr = "2017-04-27 17:33:54";
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dateTime = dtf.parseDateTime(testStr);
        log.info("java.lang.String -> joda..DateTime: {}",dateTime.toString());
        log.info(dateTime.toString("yyyy-MM-dd HH:mm:ssZZ"));
        log.info(new DateTime("2018-06-20T16:00:00.123Z").toString());
        log.info(DateTime.parse(testStr, dtf).toString());
        log.info(new DateTime(new Date()).toString());
    }

    /**
     * 使用Joda, 对日期进行日期解析和转换
     */
    @Test
    public void str_to_date(){
        String testStr = "2017-04-27 17:33:54";
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dateTime = dtf.parseDateTime(testStr);
        log.info("java.lang.String -> joda..DateTime: {}",dateTime.toString());

        String s = dateTime.toString("yyyy/MM/dd HH:mm:ss");
        log.info("joda..DateTime -> java.lang.String: {}", s);

        Date date = dateTime.toDate();
        log.info("joda..DateTime -> java.util.Date: {}", date.toString());

        DateTime dateTime1 = new DateTime(date);
        log.info("java.util.Date -> joda..DateTime: {}", dateTime1.toDateTime());
    }

}
