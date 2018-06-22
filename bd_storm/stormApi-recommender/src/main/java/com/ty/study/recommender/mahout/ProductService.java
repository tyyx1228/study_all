package com.ty.study.recommender.mahout;

import org.apache.mahout.cf.taste.recommender.Recommender;

/**
 * Created by maoxiangyi on 2016/5/9.
 */
public class ProductService {
    private Recommender recommender;

    //

    public void setRecommender(Recommender recommender) {
        this.recommender = recommender;
    }
}
