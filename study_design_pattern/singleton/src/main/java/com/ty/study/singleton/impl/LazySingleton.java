package com.ty.study.singleton.impl;

import com.ty.study.singleton.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 懒汉式：类装载时不初始化对象（没有类的实例开辟内存），当调用该类实例化方法时再产生对象（开辟内存空间）
 *
 * thread not safe
 *
 * @author relax tongyu
 * @create 2018-01-29 10:15
 **/
public class LazySingleton implements Singleton {
    private static final Logger log = LogManager.getLogger(LazySingleton.class);


    private static Singleton instance;
    private LazySingleton(){}

    public static Singleton getInstance(){
        if(instance==null){
            instance = new LazySingleton();
        }
        return instance;
    }

    @Override
    public void service() {
        log.info("service method execute... and method 'getInstance' thread not safe");
    }
}
