package com.ty.study.domain.impl;

import com.ty.study.domain.Packing;

/**
 * 纸包装
 *
 * @author relax tongyu
 * @create 2018-01-29 11:29
 **/
public class Wrapper implements Packing {

    @Override
    public String pack() {
        return WRAPPER;
    }
}
