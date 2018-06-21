package com.ty.study.mrsort;

import com.ty.study.mrjson.ResultBean;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

import com.ty.study.mrjson.ResultBean;

public class RangePartitioner extends Partitioner<ResultBean, NullWritable>{

	@Override
	public int getPartition(ResultBean key, NullWritable value, int numPartitions) {
		
		int cmp = 0;
		long movie = key.getMovie();
		if(movie<1005) cmp=0;
		if(movie>1004) cmp=1;

		return cmp;
	}

}
