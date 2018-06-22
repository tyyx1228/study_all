package com.ty.study.singleton.impl;

import com.ty.study.singleton.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 懒汉式：登记式/静态内部类实现
 *
 * thread safe
 *
 * @author relax tongyu
 * @create 2018-01-29 10:56
 **/
public class LazySingletonRegistor implements Singleton{
    public static final Logger log = LogManager.getLogger(LazySingletonRegistor.class);

    private static class SingletonHolder{
        private static final Singleton INSTANCE = new LazySingletonRegistor();
    }

    private LazySingletonRegistor(){}

    public static Singleton getInstance(){
        return SingletonHolder.INSTANCE;
    }


    @Override
    public void service() {
        log.info("servcie method execute... and method 'getInstance' thread safe");
    }
}
