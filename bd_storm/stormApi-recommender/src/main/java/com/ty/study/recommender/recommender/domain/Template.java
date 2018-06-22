package com.ty.study.recommender.recommender.domain;

import java.util.Map;

/**
 * Describe: 请补充类描述
 * Author:   maoxiangyi
 * Domain:   www.itcast.cn
 * Data:     2015/12/2.
 */
public class Template {

    private int num;//推荐的商品数量
    private int type;//返回结果的方式，比如json或Html
    private Map<Integer, Product> products;//推荐位强推的产品列表
    //1,Product1
    //5,Product2


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Map<Integer, Product> getProducts() {
        return products;
    }

    public void setProducts(Map<Integer, Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "num=" + num +
                ", type=" + type +
                ", products=" + products +
                '}';
    }
}
