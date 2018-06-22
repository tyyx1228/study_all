package com.ty.study.domain.impl;

import com.ty.study.domain.abstr.Burger;

/**
 * 素食汉堡
 *
 * @author relax tongyu
 * @create 2018-01-29 11:38
 **/
public class VegBurger extends Burger{

    @Override
    public String name() {
        return VEG_BURGER;
    }

    @Override
    public float price() {
        return 25.0f;
    }
}
