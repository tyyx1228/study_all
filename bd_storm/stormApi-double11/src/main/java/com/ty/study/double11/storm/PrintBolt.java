package com.ty.study.double11.storm;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

/**
 * Created by maoxiangyi on 2016/8/8.
 */
public class PrintBolt extends BaseBasicBolt {
    private static final long serialVersionUID = -308811092350837671L;

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        //1、接受kafka发送过来的数据
        String message = new String((byte[]) input.getValue(0));
        //2、打印数据
        System.out.println(message);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
