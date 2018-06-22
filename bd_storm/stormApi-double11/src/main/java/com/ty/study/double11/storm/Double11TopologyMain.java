package com.ty.study.double11.storm;


import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

public class Double11TopologyMain {
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
//        1、设置kafkaspout 需要一个spoutconfig
        builder.setSpout("kafkaSpout", new KafkaSpout(
                new SpoutConfig(
                        new ZkHosts("zk01:2181,zk02:2181,zk03:2181"),
                        "shuiguoshouji",
                        "/kafkaTest",
                        "kafkaSpout")),4);
        builder.setBolt("printBolt",new PrintBolt(),1).shuffleGrouping("kafkaSpout");
//        builder.setSpout("readOrderInfo",new TestSpout(),1);
//        builder.setBolt("processIndex", new FilterMessageBlot(), 2).shuffleGrouping("readOrderInfo");
//        builder.setBolt("saveResult2Redis", new Save2RedisBlot(), 10).shuffleGrouping("processIndex");
        Config conf = new Config();
        if (args != null && args.length > 0) {
            conf.setNumWorkers(2);
            StormSubmitter.submitTopologyWithProgressBar(args[0], conf, builder.createTopology());
        } else {
            conf.setMaxTaskParallelism(1);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("double11", conf, builder.createTopology());
            //一旦启动永不停止
//			Utils.sleep(10000);
//			cluster.shutdown();
        }
    }
}
