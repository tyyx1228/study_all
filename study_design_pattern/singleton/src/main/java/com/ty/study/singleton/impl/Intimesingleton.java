package com.ty.study.singleton.impl;

import com.ty.study.singleton.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 饿汉式：类装载时立即实例化该类实例（开辟内存空间）
 *
 * thread safe
 *
 * @author relax tongyu
 * @create 2018-01-29 10:39
 **/
public class Intimesingleton implements Singleton {
    private static final Logger log = LogManager.getLogger(Intimesingleton.class);

    private static Singleton instance = new Intimesingleton();
    private Intimesingleton(){}

    public static Singleton getInstance(){
        return instance;
    }

    @Override
    public void service() {
        log.info("service method execute... and method 'getInstance' thread safe");
    }
}
