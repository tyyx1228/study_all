package com.ty.study.main;

import com.ty.study.builder.MealBuilder;
import com.ty.study.domain.impl.Meal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 建造者模式主程序入口
 *
 * @author relax tongyu
 * @create 2018-01-29 12:13
 **/
public class BuilderPattarnDemo {
    private static final Logger log = LogManager.getLogger(BuilderPattarnDemo.class);

    private static MealBuilder mealBuilder;
    static {
        mealBuilder = new MealBuilder();
    }

    private void tetVegMeal(){
        Meal vegMeal = mealBuilder.perpareVegMeal();
        log.info("Display VegItems: ");
        vegMeal.showItems();
        log.info("Total Cost: {}", vegMeal.getCost());
    }

    private void tetNonVegMeal(){
        Meal nonVegMeal = mealBuilder.prepareNonVegMeal();
        log.info("Display None-VegItems: ");
        nonVegMeal.showItems();
        log.info("Total Cost: {}", nonVegMeal.getCost());
    }

    public static void main(String[] args) {
        BuilderPattarnDemo builderPattarnDemo = new BuilderPattarnDemo();
        builderPattarnDemo.tetVegMeal();
        System.out.println("----------------------------------------- up-down separate -----------------------------------------");
        builderPattarnDemo.tetNonVegMeal();
    }
}
