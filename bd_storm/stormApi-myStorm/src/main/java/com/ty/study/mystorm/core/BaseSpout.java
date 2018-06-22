package com.ty.study.mystorm.core;

/**
 * Created by maoxiangyi on 2016/6/7.
 */
public abstract class BaseSpout implements Spout,Runnable{
    public void run() {
       while (true){
           nextTuple();
       }
    }
}