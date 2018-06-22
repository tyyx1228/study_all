package com.ty.study.mystorm.wordcount;

import com.ty.study.mystorm.core.BaseSpout;
import com.ty.study.mystorm.core.MySpoutOutputCollector;
import com.ty.study.mystorm.core.MyContext;

import java.util.Map;
import java.util.Random;

/**
 * Created by maoxiangyi on 2016/6/7.
 */
public class MySentenceSpout extends BaseSpout {
    private MyContext context;
    private MySpoutOutputCollector collector;
    private Random rand;
    public void open(Map conf, MyContext context, MySpoutOutputCollector collector) {
        this.context = context;
        this.collector = collector;
        this.rand = new Random();
    }

    public void nextTuple() {
        String[] sentences = new String[] { "the cow jumped over the moon",
                "the cow jumped over the moon",
                "the cow jumped over the moon",
                "the cow jumped over the moon", "the cow jumped over the moon" };
        String sentence = sentences[rand.nextInt(sentences.length)];
        collector.emit(sentence);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
