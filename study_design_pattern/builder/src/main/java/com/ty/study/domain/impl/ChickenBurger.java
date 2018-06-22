package com.ty.study.domain.impl;

import com.ty.study.domain.abstr.Burger;

/**
 * 肌肉汉堡
 *
 * @author relax tongyu
 * @create 2018-01-29 11:42
 **/
public class ChickenBurger extends Burger{

    @Override
    public String name() {
        return CHICHEN_BURGER;
    }

    @Override
    public float price() {
        return 50.0f;
    }
}
