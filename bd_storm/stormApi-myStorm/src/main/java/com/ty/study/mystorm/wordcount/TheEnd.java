package com.ty.study.mystorm.wordcount;

import com.ty.study.mystorm.core.BaseBolt;
import com.ty.study.mystorm.core.MyContext;
import com.ty.study.mystorm.core.MyOutputCollector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maoxiangyi on 2016/6/7.
 */
public class TheEnd extends BaseBolt {
    private static final long serialVersionUID = 5678586644899822142L;
    Map<String, Integer> counters = new HashMap<String, Integer>();
    private MyOutputCollector collector;
    public void prepare(Map stormConf, MyContext context, MyOutputCollector collector) {
        this.collector = collector;
    }

    public void execute(String input) {
        System.out.println("我爱我的祖国--------"+input);
    }
}
