package com.ty.study.ackfail;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.Map;

//BaseRichBolt  需要手动ack
//BaseBasicBolt 默认自动ack
public class Bolt1 extends BaseRichBolt{
	private static final long serialVersionUID = -5283595260540124273L;
	private OutputCollector collector;
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	public void execute(Tuple input) {
		String sentence = input.getString(0);
		String[] arr = sentence.split(" ");
		for (String word:arr){
			collector.emit(input,new Values(word));
		}
		collector.ack(input);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("bolt1"));
	}
}