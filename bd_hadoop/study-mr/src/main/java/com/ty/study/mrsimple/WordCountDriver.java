package com.ty.study.mrsimple;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class WordCountDriver {
	
	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		conf.set("mapreduce.framework.name", "yarn");
		conf.set("yarn.resourcemanager.hostname", "mini1");
		
		conf.set("fs.defaultFS", "hdfs://mini1:9000");
		
		Job wcjob = Job.getInstance(conf);
		
		/*wcjob.setJarByClass(WordCountDriver.class);*/
		wcjob.setJar("c:/wc.jar");
		
		
		wcjob.setMapperClass(WordCountMapper.class);
		wcjob.setReducerClass(WordCountReducer.class);
//		wcjob.setCombinerClass(WordCountCombiner.class);
		
		wcjob.setCombinerClass(WordCountReducer.class);
		
		
		
		wcjob.setMapOutputKeyClass(Text.class);
		wcjob.setMapOutputValueClass(IntWritable.class);
		
		wcjob.setOutputKeyClass(Text.class);
		wcjob.setOutputValueClass(IntWritable.class);
		
		wcjob.setInputFormatClass(TextInputFormat.class);
		wcjob.setOutputFormatClass(TextOutputFormat.class);
		
		/*wcjob.setInputFormatClass(CombineTextInputFormat.class);
		CombineTextInputFormat.setMaxInputSplitSize(wcjob, 134217728);
		CombineTextInputFormat.setMinInputSplitSize(wcjob, 64*1024*1024);*/
		
		FileInputFormat.setInputPaths(wcjob, new Path(args[0]));
		
		Path output = new Path(args[1]);
		FileSystem fs = output.getFileSystem(conf);
		if(fs.exists(output)){
			fs.delete(output, true);
		}
		
		FileOutputFormat.setOutputPath(wcjob, output);
		wcjob.waitForCompletion(true);
		
	}
	
}
