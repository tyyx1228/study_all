package com.ty.study.mystorm.core;

import java.util.Map;

/**
 * Created by maoxiangyi on 2016/6/7.
 */
public interface Spout {
    void open(Map conf, MyContext context, MySpoutOutputCollector collector);
    void nextTuple();
}
