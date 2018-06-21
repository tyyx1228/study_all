package com.ty.study.mapsidejoin;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class TestCacheFile {
	
	static class JoinMapper extends Mapper<LongWritable, Text, LongWritable, Text>{
		
		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		
			Path[] filepath = context.getLocalCacheFiles();

			context.write(key,new Text(filepath[0].toString()));
		}
		
	}
	
	
	public static void main(String[] args) throws IOException, URISyntaxException, ClassNotFoundException, InterruptedException {
		
		Configuration conf = new Configuration();
		
		Job job = Job.getInstance(conf);
		
		job.setJarByClass(TestCacheFile.class);
		
		job.setMapperClass(JoinMapper.class);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.addCacheFile(new URI("hdfs://hadoop-server01:9000/cachefile/b.txt"));
		
		
		job.waitForCompletion(true);
		
		
		
		
		
		
	}
	
	
	
	

}
