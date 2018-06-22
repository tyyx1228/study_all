package com.ty.study.domain.impl;

import com.ty.study.domain.Shape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 圆形
 *
 * @author relax tongyu
 * @create 2018-01-28 21:47
 **/
public class Circle implements Shape {
    public static final Logger log = LogManager.getLogger(Circle.class);

    @Override
    public void draw() {
        log.info("Inside Circle::draw() method.");
    }
}
