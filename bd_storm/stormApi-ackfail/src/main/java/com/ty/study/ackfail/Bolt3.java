package com.ty.study.ackfail;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.FailedException;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.Map;
import java.util.Set;

public class Bolt3 extends BaseRichBolt{
	private static final long serialVersionUID = -5283595260540124273L;

	private OutputCollector collector;
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	public void execute(Tuple input) {
		collector.ack(input);
//		collector.fail(input);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}
}