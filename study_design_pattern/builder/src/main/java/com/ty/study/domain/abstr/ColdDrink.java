package com.ty.study.domain.abstr;

import com.ty.study.domain.Item;
import com.ty.study.domain.Packing;
import com.ty.study.domain.impl.Bottle;

/**
 * 冷饮
 *
 * @author relax tongyu
 * @create 2018-01-29 11:36
 **/
public abstract class ColdDrink implements Item {

    public static final String COKE = "Coke";
    public static final String PEPSI = "Pepsi";

    @Override
    public Packing packing() {
        return new Bottle();
    }

}
