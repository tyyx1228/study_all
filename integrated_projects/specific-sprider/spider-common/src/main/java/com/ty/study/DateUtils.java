package com.ty.study;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * @author relax tongyu
 * @create 2018-06-09 14:06
 **/
public class DateUtils {

    public static Date parse(String date, String pattern){
        return DateTimeFormat.forPattern(pattern).parseDateTime(date).toDate();
    }

    public static String toStr(Date date, String pattern){
//        DateTimeFormat.forPattern(pattern).print(new DateTime(date));
        return new DateTime(date).toString(pattern);
    }

}
