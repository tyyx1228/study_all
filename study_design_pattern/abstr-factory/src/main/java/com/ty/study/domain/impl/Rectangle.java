package com.ty.study.domain.impl;

import com.ty.study.domain.Shape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 长方形
 *
 * @author relax tongyu
 * @create 2018-01-28 21:18
 **/
public class Rectangle implements Shape{
    public static final Logger log = LogManager.getLogger(Rectangle.class);

    @Override
    public void draw() {
        log.info("Inside Rectangle::draw() method.");
    }

}
