package com.ty.study.ackfail;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.Map;

public class Bolt2 extends BaseRichBolt{
	private static final long serialVersionUID = -5283595260540124273L;

	private OutputCollector collector;
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	public void execute(Tuple input) {
		//手动锚点
		//每个单词元组是通过把输入的元组作为emit函数中的第一个参数来做锚定的。
		//通过锚定，Storm就能够得到元组之间的关联关系(输入元组触发了新的元组)，继而构建出Spout元组触发的整个消息树
		collector.emit(input,new Values(input.getString(0)+"1"));
//		//不锚点，不关心消息在下一阶段处理的成功失败
//		collector.emit(new Values(input.getString(0)+"1"));

		//手动ack
		collector.ack(input);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("bolt2"));
	}
}