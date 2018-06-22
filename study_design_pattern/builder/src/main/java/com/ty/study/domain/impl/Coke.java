package com.ty.study.domain.impl;

import com.ty.study.domain.abstr.ColdDrink;

/**
 * 可乐
 *
 * @author relax tongyu
 * @create 2018-01-29 11:57
 **/
public class Coke extends ColdDrink{

    @Override
    public String name() {
        return COKE;
    }

    @Override
    public float price() {
        return 30.0f;
    }
}
