package com.inbasis;

import org.jsoup.Connection.*;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;

/**
 * @author IBS tongyu
 * @create 2018-12-24 16:32
 **/
public class Punch {

    public static final String BASE_URL = "http://oo.inbasis.com:8088/ibsyygl/servlet/defaultDispatcher";


    public static void main(String[] args) throws IOException {
        punch();
    }

    public static void punch() throws IOException{
        Map loginHead = getLoginHead();
        Map loginParams = getLoginParams();
        Response re = Jsoup.connect(BASE_URL)
                .headers(loginHead)
                .data(loginParams)
                .method(Method.POST)
                .timeout(20000)
                .execute();

        int statusCode = re.statusCode();
        System.out.println(String.format("登陆请求返回状态码：%s", statusCode));

        Map<String, String> cookies = re.cookies();
        System.out.println(cookies.size());

        String body = re.body();
        System.out.println(String.format("登陆响应体：%s", body));


        Map punchHead = getPunchHead();
        Map punchParams = getPunchParams();
        Response punchResponse = Jsoup.connect(BASE_URL)
                .headers(punchHead)
                .data(punchParams)
                .cookies(cookies)
                .method(Method.POST)
                .timeout(20000)
                .execute();

        int i = punchResponse.statusCode();
        System.out.println(String.format("打卡请求返回状态码：%s", statusCode));
        String puchBody = punchResponse.body();
        System.out.println(String.format("打卡响应体：%s", puchBody));

    }


    public static void login() throws IOException {
        Map loginHead = getLoginHead();
        Map loginParams = getLoginParams();
        Response re = Jsoup.connect(BASE_URL)
                .headers(loginHead)
                .data(loginParams)
                .method(Method.POST)
                .timeout(20000)
                .execute();

        int statusCode = re.statusCode();
        System.out.println(String.format("登陆请求返回状态码：%s", statusCode));

        Map<String, String> cookies = re.cookies();
        System.out.println(cookies.size());

        String body = re.body();
        System.out.println(String.format("登陆响应体：%s", body));

    }


    public static Map getLoginParams() throws IOException {
        return getProp("login.properties");
    }


    public static Map getLoginHead() throws IOException {
        return getProp("login-head.properties");
    }

    public static Map getPunchParams() throws IOException {
        return getProp("punch.properties");
    }

    public static Map getPunchHead() throws IOException {
        return getProp("login-head.properties");
    }



    public static Properties getProp(String path) throws IOException{
        InputStream in = Punch.class.getClassLoader().getResourceAsStream(path);
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        Properties prop = new Properties();
        prop.load(bf);
        return prop;
    }



}
