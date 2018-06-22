package com.ty.study.kafka;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by maoxiangyi on 2016/3/31.
 */
public class KafkaConsumer {
    public static void main(String[] args) throws Exception {
        ArrayBlockingQueue queue = new ArrayBlockingQueue(1000);
        consumerMessageInqueue(queue);
        saveMessage2HDFS(queue);
    }

    public static void saveMessage2HDFS(ArrayBlockingQueue queue) throws Exception {
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop01:9000"), new Configuration(), "root");
        FSDataOutputStream os = fs.append(new Path("/sanbox/kafka/data.log.q"));
        try {
            String message = (String) queue.take();
            System.out.println(message);
            while (message != null) {
                os.write(message.getBytes());
                message = (String) queue.take();
            }
        } catch (Exception e) {
            try {
                os.hflush();
                os.hsync();
                os.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    public static void consumerMessageInqueue(ArrayBlockingQueue queue) {
        // main方法
        Properties props = new Properties();
        props.put("group.id", "gid-11");
        props.put("zookeeper.connect", "zk01:2181,zk02:2181,zk03:2181");
        props.put("auto.offset.reset", "largest");
        props.put("auto.commit.interval.ms", "1000");
        props.put("partition.assignment.strategy", "roundrobin");
        ConsumerConfig config = new ConsumerConfig(props);
        String topic = "kafkaTest";
        //只要ConsumerConnector还在的话，consumer会一直等待新消息，不会自己退出
        ConsumerConnector consumerConn = kafka.consumer.Consumer.createJavaConsumerConnector(config);
        Map<String, Integer> topicCountMap = new HashMap<>();
        topicCountMap.put(topic, 20);
        Map<String, List<KafkaStream<byte[], byte[]>>> topicStreamsMap =
                consumerConn.createMessageStreams(topicCountMap);
        //取出 `kafkaTest` 对应的 streams
        List<KafkaStream<byte[], byte[]>> streams = topicStreamsMap.get(topic);
        //创建一个容量为20的线程池
        ExecutorService executor = Executors.newFixedThreadPool(20);
        //创建20个consumer threads
        for (int i = 0; i < streams.size(); i++) {
            executor.execute(new KafkaConsumer2Queue("消费者" + (i + 1), streams.get(i), queue));
        }
    }
}
