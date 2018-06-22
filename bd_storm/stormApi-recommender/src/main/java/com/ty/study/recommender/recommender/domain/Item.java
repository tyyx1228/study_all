package com.ty.study.recommender.recommender.domain;

/**
 * Describe: 请补充类描述
 * Author:   maoxiangyi
 * Domain:   www.itcast.cn
 * Data:     2015/12/3.
 */
public class Item implements Comparable {
    private String id;
    private double weight;

    public Item() {
    }

    public Item(String id, double weight) {
        this.id = id;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        Item item = (Item) o;
        if (weight > item.getWeight()) {
            return 1;
        }
        if (weight < item.getWeight()) {
            return -1;
        }
        return 0;
    }
}
