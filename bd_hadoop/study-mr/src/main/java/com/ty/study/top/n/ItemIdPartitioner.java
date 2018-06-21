package com.ty.study.top.n;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;


public class ItemIdPartitioner extends Partitioner<OrderBean, NullWritable>{

	@Override
	public int getPartition(OrderBean key, NullWritable value, int numPartitions) {
		//指定item_id相同的bean发往相同的reducer task
		return (key.getItemid().hashCode() & Integer.MAX_VALUE) % numPartitions;
		
	}

}
