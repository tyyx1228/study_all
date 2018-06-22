package com.ty.study.domain.impl;

import com.ty.study.domain.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 绿色
 *
 * @author relax tongyu
 * @create 2018-01-28 22:16
 **/
public class Green implements Color {
    public static final Logger log = LogManager.getLogger(Green.class);

    @Override
    public void fill() {
        log.info("Inside Green::fill() method.");
    }
}
