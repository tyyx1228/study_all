package com.ty.study.mystorm.wordcount;

import com.ty.study.mystorm.core.BaseBolt;
import com.ty.study.mystorm.core.MyContext;
import com.ty.study.mystorm.core.MyOutputCollector;

import java.util.Map;

/**
 * Created by maoxiangyi on 2016/6/7.
 */
public class MySplitBolt extends BaseBolt {
    private MyOutputCollector collector;
    public void prepare(Map stormConf, MyContext context, MyOutputCollector collector) {
        this.collector = collector;
    }

    public void execute(String sentence) {
        String[] words = sentence.split(" ");
        for (String word : words) {
            word = word.trim();
            if (!word.isEmpty()) {
                word = word.toLowerCase();
                collector.emit(word);
            }
        }
    }
}
