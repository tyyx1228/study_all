package com.ty.study.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 抽象工厂生产者
 *
 * @author relax tongyu
 * @create 2018-01-28 22:47
 **/
public class FactoryProducer {
    private static final Logger log = LogManager.getLogger(FactoryProducer.class);

    public static <T> T getFactory(Class<? extends T> clazz){
        T obj = null;
        try {
            obj =  (T) Class.forName(clazz.getName()).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            log.error("无法实例化工厂生产者", e);
        }
        return obj;
    }
}
