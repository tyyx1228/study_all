package com.ty.study.double11.storm;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import com.ty.study.double11.domain.PaymentInfo;

import java.util.Map;

/**
 * Created by maoxiangyi on 2016/6/16.
 */
public class TestSpout extends BaseRichSpout {
    private static final long serialVersionUID = 8089353018732444763L;
    SpoutOutputCollector collector;
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("order"));
    }

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
       this.collector = collector;
    }

    @Override
    public void nextTuple() {
        collector.emit(new Values(new PaymentInfo().random()));
    }
}
