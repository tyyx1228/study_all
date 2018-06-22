package com.ty.study.double11.dataSource;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;
import org.apache.log4j.Logger;


public class MyLogPartitioner implements Partitioner {
    private static Logger logger = Logger.getLogger(MyLogPartitioner.class);

    public MyLogPartitioner(VerifiableProperties props) {
    }

    public int partition(Object obj, int numPartitions) {
        return Math.abs(obj.hashCode()%numPartitions);
    }

}
