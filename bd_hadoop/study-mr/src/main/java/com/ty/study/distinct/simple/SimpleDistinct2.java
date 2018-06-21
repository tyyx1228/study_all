package com.ty.study.distinct.simple;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class SimpleDistinct2 {

	public static class DistinctMapper extends Mapper<LongWritable, Text, LineBean, NullWritable> {

		LineBean k = new LineBean();

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			String line = value.toString();
			String[] split = line.split("\t");
			
			k.set(new Text(split[0]),new LongWritable(Long.parseLong(split[1])));
			context.write(k, NullWritable.get());

		}

	}

	public static class DistinctReducer extends Reducer<LineBean, NullWritable, LineBean, NullWritable> {

		@Override
		protected void reduce(LineBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {

			context.write(key, NullWritable.get());

		}

	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);

		job.setJarByClass(SimpleDistinct2.class);
		job.setMapperClass(DistinctMapper.class);
		job.setReducerClass(DistinctReducer.class);
		job.setOutputKeyClass(LineBean.class);
		job.setOutputValueClass(NullWritable.class);

		FileInputFormat.setInputPaths(job, new Path("x:/wordcount/distinct/input"));
		FileOutputFormat.setOutputPath(job, new Path("x:/wordcount/distinct/output"));
		job.waitForCompletion(true);
	}

}
