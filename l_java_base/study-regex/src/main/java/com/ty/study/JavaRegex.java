package com.ty.study;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.io.*;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * 学习正则
 *
 * @author relax tongyu
 * @create 2018-05-07 14:56
 **/
public class JavaRegex {
    public static void main(String[] args) throws IOException {

//        String tag = "2018/5/7 16:12:04\0sdfsc\1NMDH#COLON#30MAX01CT601OUT_BZ\2slllliiic";
        String tag = "2018/5/7 16:10:24^@NMDH:30DEH01FA103XQ01^@NMDH:30MAX01CT601OUT_BZ^@NMDH30MAX01CT601OUT.value<21#suffix#NMDH30DEH01FA103XQ01.value>100#suffix#&&#suffix#NMDH30MAX01CT601OUT.value>55#suffix#||#suffix#^@NMDH30MAX01CT601OUT.value>=26#suffix#NMDH30MAX01CT601OUT.value<=50#suffix#&&#suffix#^@NMDH:30MAX01CT601OUT,NMDH:30DEH01FA103XQ01,,NMDH:30MAX01CT601OUT^@NMDH:30MAX01CT601OUT,NMDH:30MAX01CT601OUT^@BJ^@1^ANMDH:30MAX01CT601OUT^B2018/3/28 10:12:02^B46.17969^BTrue^CNMDH:30DEH01FA103XQ01^B2018/5/7 16:10:24^B2999.2^BTrue";
        InputStream in = JavaRegex.class.getClassLoader().getResourceAsStream("regex.properties");
        Properties prop = new Properties();
        prop.load(in);

//        String st = "\\.*(NMDH#COLON#30MAX01CT601OUT)\\.*";
        String st = prop.getProperty("regex");
        Pattern regex = Pattern.compile(st);
        boolean b = regex.matcher(new String(tag)).find();
        System.out.println(b);

    }

}
