package com.ty.study.wordcount;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.lib.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class WordCount {
	static class WordcountMapper extends Mapper<LongWritable, Text, Text, LongWritable>{
		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			String line = value.toString();
			String[] words = line.split(" ");
			for(String w:words){
				
				context.write(new Text(w),new LongWritable(1));
			}
		}
		@Override
		protected void cleanup(Context context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			System.out.println("haha");
		}
		
	}
	
	static class WordcountReducer extends Reducer<Text, LongWritable, Text, LongWritable>{
		@Override
		protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {

			long count =0;
			for(LongWritable v:values){
				count += v.get();
			}
			context.write(key, new LongWritable(count));
			
		}
		

	}
	
	
	static class WordcountCombiner extends Reducer<Text, LongWritable, Text, LongWritable>{
		@Override
		protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
			long count =0;
			for(LongWritable v:values){
				count += v.get();
			}
			context.write(key, new LongWritable(count));
		}
		
	}	
	
	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		// conf.set("hadoop.tmp.dir", "d:/hadooptmp/");
//		conf.set("mapred.map.output.compress.codec","true");
//		conf.set("mapred.map.output.compress.codec", "org.apache.hadoop.io.compress.GzipCodec");
//		conf.set("mapreduce.output.fileoutputformat.compress", "true");
//		conf.set("mapreduce.output.fileoutputformat.compress.codec", "org.apache.hadoop.io.compress.GzipCodec");
		Job job = Job.getInstance(conf);
		
		job.setJarByClass(WordCount.class);
		job.setMapperClass(WordcountMapper.class);
		job.setReducerClass(WordcountReducer.class);
		//job.setCombinerClass(WordcountCombiner.class);
		//job.setUser("hadoop");
//		job.setInputFormatClass(CombineTextInputFormat.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		job.setNumReduceTasks(0);
		
		
//		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		
//		FileInputFormat.setInputPaths(job, new Path(args[0]));
//		FileOutputFormat.setOutputPath(job, new Path(args[1]));
//		FileInputFormat.setInputPaths(job, new Path("F:/bigdata-material-data/2013072404-http-combinedBy-1373892200521-log-1"));
		FileInputFormat.setInputPaths(job, new Path("x:/wordcount/srcdata"));
		FileOutputFormat.setOutputPath(job, new Path("x:/wordcount/test"+new Random().nextInt(100)));
		
		job.waitForCompletion(true);
		
		
		
	}
	

}
