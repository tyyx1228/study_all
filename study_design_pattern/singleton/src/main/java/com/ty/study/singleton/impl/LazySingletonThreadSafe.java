package com.ty.study.singleton.impl;

import com.ty.study.singleton.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 懒汉式：类装载时不初始化对象（没有类的实例开辟内存），当调用该类实例化方法时再产生对象（开辟内存空间）
 *
 * thread safe
 *
 * @author relax tongyu
 * @create 2018-01-29 10:30
 **/
public class LazySingletonThreadSafe implements Singleton{
    private static final Logger log = LogManager.getLogger(LazySingletonThreadSafe.class);

    private static Singleton instance;
    private LazySingletonThreadSafe(){}

    public static synchronized Singleton getInstance(){
        if(instance==null){
            instance = new LazySingletonThreadSafe();
        }
        return instance;
    }

    @Override
    public void service() {
        log.info("service method execute... and method 'getInstance' thread safe");
    }
}
