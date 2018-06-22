package com.ty.study.ackfail;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 功能说明
 * 伪造数据源，在storm框架调用nextTuple()方法时，发送英文句子出去。
 */
public class MySpout extends BaseRichSpout {
    private static final long serialVersionUID = 5028304756439810609L;
    // key:messageId,Data
    private HashMap<String, String> waitAck = new HashMap<String, String>();
    private SpoutOutputCollector collector;

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("sentence"));
    }

    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    public void nextTuple() {
        String sentence = "the cow jumped over the moon";
        String messageId = UUID.randomUUID().toString().replaceAll("-", "");
        waitAck.put(messageId, sentence);
        //指定messageId，开启ackfail机制
        collector.emit(new Values(sentence));
    }

    @Override
    public void ack(Object msgId) {
        System.out.println("消息处理成功:" + msgId);
        System.out.println("删除缓存中的数据...");
        waitAck.remove(msgId);
    }

    @Override
    public void fail(Object msgId) {
        System.out.println("消息处理失败:" + msgId);
        System.out.println("重新发送失败的信息...");
        collector.emit(new Values(waitAck.get(msgId)),msgId);
    }
}