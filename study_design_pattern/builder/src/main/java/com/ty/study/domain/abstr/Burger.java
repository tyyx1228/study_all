package com.ty.study.domain.abstr;

import com.ty.study.domain.Item;
import com.ty.study.domain.Packing;
import com.ty.study.domain.impl.Wrapper;

/**
 * 汉堡
 *
 * @author relax tongyu
 * @create 2018-01-29 11:34
 **/
public abstract class Burger implements Item{
    public static final String VEG_BURGER = "Veg Burger";
    public static final String CHICHEN_BURGER = "Chicken Burger";

    @Override
    public Packing packing() {
        return new Wrapper();
    }


}
