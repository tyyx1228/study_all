package com.ty.study.stream;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.Map;

public class DistributeWordByTypeBolt extends BaseRichBolt {
    private static final long serialVersionUID = 1L;
    private OutputCollector collector;

    public void prepare(Map stormConf, TopologyContext context,
                        OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        String type = input.getString(0);
        String word = input.getString(1);
        switch (type) {
            case Type.NUMBER:
                emit("stream-number-saver", type, input, word);
                break;
            case Type.STRING:
                emit("stream-string-saver", type, input, word);
                break;
            case Type.SIGN:
                emit("stream-sign-saver", type, input, word);
                break;
            default:
                emit("stream-other", type, input, word);
        }
        collector.ack(input);
    }
    private void emit(String streamId, String type, Tuple input, String word) {
        collector.emit(streamId, input, new Values(type, word));
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declareStream("stream-number-saver", new Fields("type", "word"));
        declarer.declareStream("stream-string-saver", new Fields("type", "word"));
        declarer.declareStream("stream-sign-saver", new Fields("aaa", "word"));
        declarer.declareStream("stream-other", new Fields("type", "word"));
    }
}
