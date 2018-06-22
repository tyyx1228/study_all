package com.ty.study.wc;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

public class WordCountBolt extends BaseBasicBolt {
    private static final long serialVersionUID = 5678586644899822142L;
    // 用来保存最后计算的结果key=单词，value=单词个数
    Map<String, Integer> counters = new HashMap<String, Integer>();
    //该方法只会被调用一次，用来初始化
    public void prepare(Map stormConf, TopologyContext context) {
        super.prepare(stormConf, context);
    }
    /**
     * 将collector中的元素存放在成员变量counters（Map）中.
     * 如果counters（Map）中已经存在该元素，getValule并对Value进行累加操作。
     */
    public void execute(Tuple input, BasicOutputCollector collector) {
        String str = (String)input.getValueByField("word");
        Integer num =input.getIntegerByField("num");
        //三种方式获取数据
//        input.getValue(0);
//        input.getString(0);
//        input.getValueByField("word");
        System.out.println("----------------"+Thread.currentThread().getId()+"     "+str);
        if (!counters.containsKey(str)) {
            counters.put(str, num);
        } else {
            Integer c = counters.get(str) + num;
            counters.put(str, c);
        }
        System.out.println("WordCountBolt 统计单词："+counters);
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // TODO Auto-generated method stub
    }
}