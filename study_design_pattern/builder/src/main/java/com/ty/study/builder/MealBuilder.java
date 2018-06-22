package com.ty.study.builder;

import com.ty.study.domain.impl.*;

/**
 * 餐饮建造者
 *
 * @author relax tongyu
 * @create 2018-01-29 12:09
 **/
public class MealBuilder {

    public Meal perpareVegMeal(){
        Meal meal = new Meal();
        meal.addItem(new VegBurger());
        meal.addItem(new Coke());
        return meal;
    }

    public Meal prepareNonVegMeal(){
        Meal meal = new Meal();
        meal.addItem(new ChickenBurger());
        meal.addItem(new Pepsi());
        return meal;
    }
}
