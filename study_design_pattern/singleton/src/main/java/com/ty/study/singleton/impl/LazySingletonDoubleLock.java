package com.ty.study.singleton.impl;

import com.ty.study.singleton.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 懒汉式：双重校验锁
 *
 * thread safe and high performence
 *
 * @author relax tongyu
 * @create 2018-01-29 10:47
 **/
public class LazySingletonDoubleLock implements Singleton{
    public static final Logger log = LogManager.getLogger(LazySingletonDoubleLock.class);

    private volatile static Singleton instance;
    private LazySingletonDoubleLock(){}

    public static Singleton getInstance(){
        if(instance==null){
            synchronized (LazySingletonDoubleLock.class){
                if(instance==null){
                    instance = new LazySingletonDoubleLock();
                }
            }
        }
        return instance;
    }

    @Override
    public void service() {
        log.info("service method execute... and method 'getInstance' thread safe");
    }
}
