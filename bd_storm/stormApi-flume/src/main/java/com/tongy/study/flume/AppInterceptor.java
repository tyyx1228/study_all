package com.tongy.study.flume;


import org.apache.commons.lang.StringUtils;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义一个点击流收集的拦截器
 */
public class AppInterceptor implements Interceptor {
    //4、定义成员变量appId，用来接收从配置文件中读取的信息
    private String appId;

    public AppInterceptor(String appId) {
        this.appId = appId;
    }

    /**
     * 单条数据进行处理
     * @param event
     * @return
     */
    public Event intercept(Event event) {
        String message = null;
        try {
            message = new String(event.getBody(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            message = new String(event.getBody());
        }
        //处理逻辑
        if (StringUtils.isNotBlank(message)) {
            message = "aid:"+appId+"||msg:" +message;
            event.setBody(message.getBytes());
            //正常逻辑应该执行到这里
            return event;
        }
        //如果执行以下代码，表示拦截失效了。
        return event;
    }

    /**
     * 批量数据数据进行处理
     * @param list
     * @return
     */
    public List<Event> intercept(List<Event> list) {
        List<Event> resultList = new ArrayList<Event>();
        for (Event event : list) {
            Event r = intercept(event);
            if (r != null) {
                resultList.add(r);
            }
        }
    return resultList;
}

    public void close() {

    }

    public void initialize() {

    }

    public static  class AppInterceptorBuilder implements Interceptor.Builder{
        //1、获取配置文件的appId
        private String appId;

        public Interceptor build() {
            //3、构造拦截器
            return new AppInterceptor(appId);
        }

        public void configure(Context context) {
            //2、当出现default之后，就是点击流告警系统
           this.appId =  context.getString("appId","default");
            System.out.println("appId:"+appId);
        }
    }
}

