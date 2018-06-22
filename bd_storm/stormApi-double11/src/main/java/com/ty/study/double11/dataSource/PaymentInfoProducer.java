package com.ty.study.double11.dataSource;

import com.ty.study.double11.domain.PaymentInfo;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

public class PaymentInfoProducer {
    public final static String TOPIC = "double11";
    public static void main(String[] args) {
        // 1、设置配置信息
        Properties props = getProperties();
        // 2、创建producer
        Producer<String, String> producer = new Producer<String, String>(new ProducerConfig(props));
        // 3、发送数据
        int index =0;
        while (true) {
            producer.send(new KeyedMessage<String, String>(TOPIC,index+"",new PaymentInfo().random()));
            index++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static Properties getProperties() {
        Properties props = new Properties();
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("metadata.broker.list",
                "kafka01:9092,kafka02:9092,kafka03:9092");
        props.put("request.required.acks", "1");
        props.put("partitioner.class", "com.ty.study.bigdata.double11.double11.dataSource.MyLogPartitioner");
        return props;
    }
}
