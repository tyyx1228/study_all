package com.ty.study.mystorm.wordcount;

import com.ty.study.mystorm.core.BaseBolt;
import com.ty.study.mystorm.core.MyContext;
import com.ty.study.mystorm.core.MyOutputCollector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maoxiangyi on 2016/6/7.
 */
public class MyWordCountBolt extends BaseBolt {
    private static final long serialVersionUID = 5678586644899822142L;
    Map<String, Integer> counters = new HashMap<String, Integer>();
    private MyOutputCollector collector;
    public void prepare(Map stormConf, MyContext context, MyOutputCollector collector) {
        this.collector = collector;
    }

    public void execute(String input) {
        String str = input;
        if (!counters.containsKey(str)) {
            counters.put(str, 1);
        } else {
            Integer c = counters.get(str) + 1;
            counters.put(str, c);
        }
        collector.emit(counters.toString());
    }
}
