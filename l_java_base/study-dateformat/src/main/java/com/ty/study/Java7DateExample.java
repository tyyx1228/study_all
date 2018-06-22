package com.ty.study;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * java8以下日期转换，解析操作
 *
 * @author relax tongyu
 * @create 2018-06-18 13:43
 **/
@Slf4j
public class Java7DateExample {

    @Test
    public void java7Date(){
        String testStr = "2017-03-22 05:22:49";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(testStr);
            log.info("java.lang.String -> java.util.Date: {}", date.toString());

            String d = sdf.format(date);
            log.info("java.util.Date -> java.lang.String: {}", d);

        } catch (ParseException e) {
            log.error("", e);
        }
    }

}
