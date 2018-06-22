package com.ty.study.mystorm.core;

import java.util.Map;

/**
 * Created by maoxiangyi on 2016/6/7.
 */
public interface Bolt {
    void prepare(Map stormConf, MyContext context, MyOutputCollector collector);
    void execute(String tuple);
}
