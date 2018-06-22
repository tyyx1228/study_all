package com.ty.study.ackfail;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;

public class AckFailTopologyMain {
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout1", new MySpout(), 1);
        builder.setBolt("bolt1", new Bolt1(), 1).shuffleGrouping("spout1");
        builder.setBolt("bolt2", new Bolt2(), 1).shuffleGrouping("bolt1");
        builder.setBolt("bolt3", new Bolt3(), 1).shuffleGrouping("bolt2");

        Config conf = new Config();
        conf.setDebug(false);
        conf.setNumAckers(1);
        conf.setMaxSpoutPending(5000);

        if (args != null && args.length > 0) {
            conf.setNumWorkers(3);
            StormSubmitter.submitTopologyWithProgressBar(args[0], conf, builder.createTopology());
        } else {
            conf.setMaxTaskParallelism(3);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("word-count", conf, builder.createTopology());
            //指定本地模式运行多长时间之后停止，如果不显式的关系程序将一直运行下去
            //Utils.sleep(10000);
            //cluster.shutdown();
        }
    }
}
