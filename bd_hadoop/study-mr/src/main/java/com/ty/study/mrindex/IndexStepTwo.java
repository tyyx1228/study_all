package com.ty.study.mrindex;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class IndexStepTwo {

	static class IndexStepTwoMapper extends Mapper<LongWritable, Text, Text, Text> {

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			String line = value.toString();

			String[] word_file = line.split("--");
			context.write(new Text(word_file[0]), new Text(word_file[1]));

		}

	}

	static class IndexStepTwoReducer extends Reducer<Text, Text, Text, Text> {

		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

			StringBuffer sb = new StringBuffer();
			for (Text value : values) {

				sb.append(value.toString());

			}

			context.write(key, new Text(sb.toString()));
		}
	}

	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();

		Job job1 = Job.getInstance(conf);
		job1.setJarByClass(IndexStepTwo.class);

		job1.setOutputKeyClass(Text.class);
		job1.setOutputValueClass(Text.class);

		FileInputFormat.setInputPaths(job1, new Path("c:/wordcount/inverindexoutput-step1"));
		FileOutputFormat.setOutputPath(job1, new Path("c:/wordcount/inverindexoutput-step2"));
		// FileInputFormat.setInputPaths(job, new Path(args[0]));
		// FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job1.setMapperClass(IndexStepTwoMapper.class);
		job1.setReducerClass(IndexStepTwoReducer.class);

		job1.waitForCompletion(true);

	}

}
