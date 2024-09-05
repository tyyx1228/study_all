package com.ty.study;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * 请求工具
 *
 * @author relax tongyu
 * @create 2018-06-09 11:48
 **/
public class RequestUtil {


    public static Document request(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    public static Document request(String url, int times) throws IOException {
        return request(url, 1, times);
    }

    public static Document request(String url, int retryTimes, int times) throws IOException {
        try {
            return request(url);
        }catch (Exception e){
            if(retryTimes>times){
                throw e;
            }
            return request(url, ++retryTimes, times);
        }
    }


}
