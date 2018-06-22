package com.ty.study.loganalyzer.storm.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import com.ty.study.loganalyzer.storm.domain.LogMessage;
import com.ty.study.loganalyzer.storm.utils.LogAnalyzeHandler;

/**
 * Describe:
 * Author:   maoxiangyi
 * Domain:   www.itcast.cn
 * Data:     2015/11/16.
 */
public class ProcessMessage extends BaseBasicBolt {
    private static final long serialVersionUID = 1143334870493183100L;

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        LogMessage logMessage = (LogMessage) input.getValueByField("message");
        LogAnalyzeHandler.process(logMessage);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
