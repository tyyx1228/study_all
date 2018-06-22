package com.ty.study.wc;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.util.Map;
import java.util.Random;

/**
 *	功能说明
 *	伪造数据源，在storm框架调用nextTuple()方法时，发送英文句子出去。
 */
public class RandomSentenceSpout extends BaseRichSpout {
	private static final long serialVersionUID = 5028304756439810609L;
	//用来收集Spout输出的tuple
	SpoutOutputCollector collector;
	Random rand;
	//该方法调用一次，主要由storm框架传入SpoutOutputCollector
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this.collector = collector;
		rand = new Random();
	}

	/**
	 * 上帝之手
	 *  while(true){
	 *      spout.nexTuple()
	 *  }
	 */
	public void nextTuple() {
		String[] sentences = new String[] { "the cow jumped over the moon",
				"the cow jumped over the moon",
				"the cow jumped over the moon",
				"the cow jumped over the moon", "the cow jumped over the moon" };
		String sentence = sentences[rand.nextInt(sentences.length)];
		collector.emit(new Values(sentence));
//		System.out.println("RandomSentenceSpout 发送数据："+sentence);
	}
	//消息源可以发射多条消息流stream。多条消息流可以理解为多中类型的数据。
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("sentence"));
	}

}