package com.ty.study.flowcountpartition;

import java.util.HashMap;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class ProvincePartitioner extends Partitioner<Text, FlowBean>{

	private static HashMap<String, Integer> pmap = new HashMap<String, Integer>();
	
	static {
		pmap.put("136", 0);
		pmap.put("137", 1);
		pmap.put("138", 2);
		pmap.put("139", 3);
		
	}
	
	
	
	@Override
	public int getPartition(Text key, FlowBean value, int numPartitions) {
		
		String prefix = key.toString().substring(0,3);
		Integer partNum = pmap.get(prefix);
		
		return (partNum==null?4:partNum);
	}

}
