package com.ty.study.domain.impl;

import com.ty.study.domain.Item;
import com.ty.study.domain.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 餐饮
 *
 * @author relax tongyu
 * @create 2018-01-29 12:02
 **/
public class Meal {
    private static final Logger log = LogManager.getLogger(Meal.class);

    private List<Item> items = new ArrayList<Item>();

    public void addItem(Item item){
        items.add(item);
    }

    public float getCost(){
        float cost = 0.0f;
        for(Item item: items){
            cost += item.price();
        }
        return cost;
    }

    public void showItems(){
        for (Item item: items){
            log.info("Item: {}, Packing: {}, Price: {}", item.name(), item.packing().pack(), item.price());
        }
    }
}
