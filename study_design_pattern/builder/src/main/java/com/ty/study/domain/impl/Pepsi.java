package com.ty.study.domain.impl;

import com.ty.study.domain.abstr.ColdDrink;

/**
 * 百世
 *
 * @author relax tongyu
 * @create 2018-01-29 12:01
 **/
public class Pepsi extends ColdDrink{

    @Override
    public String name() {
        return PEPSI;
    }

    @Override
    public float price() {
        return 35.0f;
    }
}
