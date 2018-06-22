package com.ty.study.stream;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class StreamTopologyMain {
    public static void main(String[] args) throws Exception {
        //1、创建TopologyBuilder
        TopologyBuilder builder = new TopologyBuilder();

        //2、发送三类流 一类数字、一类单词，一类特殊符号
        builder.setSpout("spout-number", new ProduceRecordSpout(Type.NUMBER, new String[]{"111 222 333", "80966 31"}), 1);
        builder.setSpout("spout-string", new ProduceRecordSpout(Type.STRING, new String[]{"abc ddd fasko", "hello the word"}), 1);
        builder.setSpout("spout-sign", new ProduceRecordSpout(Type.SIGN, new String[]{"++ -*% *** @@", "{+-} ^#######"}), 1);

        //3、汇聚三类流，并对每类流中的数据进行分割，拼装成新的数据类型输出 type，内容
        builder.setBolt("bolt-splitter", new SplitRecordBolt(), 2)
                .shuffleGrouping("spout-number")
                .shuffleGrouping("spout-string")
                .shuffleGrouping("spout-sign");

        //4、按照字段进行分组获取上游发送的数据，并根据数据类型发送三条流出去
        builder.setBolt("bolt-distributor", new DistributeWordByTypeBolt(), 3)
                .fieldsGrouping("bolt-splitter", new Fields("type"));

        //5、对每类数据进行分类存放
        builder.setBolt("bolt-number-saver", new SaveDataBolt(Type.NUMBER), 3).shuffleGrouping("bolt-distributor", "stream-number-saver");
        builder.setBolt("bolt-string-saver", new SaveDataBolt(Type.STRING), 3).shuffleGrouping("bolt-distributor", "stream-string-saver");
        builder.setBolt("bolt-sign-saver", new SaveDataBolt(Type.SIGN), 3).fieldsGrouping("bolt-distributor", "stream-sign-saver",new Fields("aaa"));

        //6、定义config
        Config conf = new Config();
        conf.setDebug(false);
        String name = StreamTopologyMain.class.getSimpleName();
        if (args != null && args.length > 0) {
            String nimbus = args[0];
            conf.put(Config.NIMBUS_HOST, nimbus);
            conf.setNumWorkers(3);
            StormSubmitter.submitTopologyWithProgressBar(name, conf, builder.createTopology());
        } else {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology(name, conf, builder.createTopology());
            Thread.sleep(60 * 60 * 1000);
            cluster.shutdown();
        }
    }
}
