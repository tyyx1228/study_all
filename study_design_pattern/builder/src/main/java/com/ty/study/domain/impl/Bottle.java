package com.ty.study.domain.impl;

import com.ty.study.domain.Packing;

/**
 * 瓶装
 *
 * @author relax tongyu
 * @create 2018-01-29 11:29
 **/
public class Bottle implements Packing {

    @Override
    public String pack() {
        return BOTTLE;
    }
}
