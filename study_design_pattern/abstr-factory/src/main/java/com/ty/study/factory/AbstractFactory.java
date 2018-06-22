package com.ty.study.factory;

import com.ty.study.domain.Color;
import com.ty.study.domain.Shape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 抽象工厂
 *
 * @author relax tongyu
 * @create 2018-01-28 22:20
 **/
public abstract class AbstractFactory {
    protected static Logger log;

    public AbstractFactory() {
        log = LogManager.getLogger(this.getClass());
    }

    protected <T> T getBean(Class<? extends T> clazz) {
        T obj = null;
        try {
            obj =  (T) Class.forName(clazz.getName()).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            log.error("无法实例化对象", e);
        }
        return obj;
    }

    public abstract Color getColor(Class<? extends Color> clazz) throws NoSuchMethodException;
    public abstract Shape getShape(Class<? extends Shape> clazz) throws NoSuchMethodException;
}
