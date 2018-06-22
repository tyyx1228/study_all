package com.ty.study.stream;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;


public class SaveDataBolt extends BaseRichBolt {
    private static final long serialVersionUID = 1L;
    private OutputCollector collector;
    private String type;

    public SaveDataBolt(String type) {
        this.type = type;
    }

    public void prepare(Map stormConf, TopologyContext context,
                        OutputCollector collector) {
        this.collector = collector;
    }

    public void execute(Tuple input) {
        System.out.println("[" + type + "] " +
                "SourceComponent=" + input.getSourceComponent() +
                ", SourceStreamId=" + input.getSourceStreamId() +
                ", type=" + input.getString(0) +
                ", value=" + input.getString(1));
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    }
}
