package com.ty.study.mrmultinputs;

import java.io.IOException;

import com.ty.study.wordcount.WordCount;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.lib.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.ty.study.wordcount.WordCount;

public class MultiInputsDemo {
	static class TextMapperA extends Mapper<LongWritable, Text, Text, LongWritable> {

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			String line = value.toString();
			String[] words = line.split(" ");
			for (String w : words) {
				context.write(new Text(w), new LongWritable(1));
			}
		}
	}

	static class SequenceMapperB extends Mapper<Text, LongWritable, Text, LongWritable> {
		@Override
		protected void map(Text key, LongWritable value, Context context) throws IOException, InterruptedException {

			context.write(key, value);

		}
	}

	static class SameReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

		@Override
		protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {

			long count = 0;
			for (LongWritable v : values) {

				count += v.get();

			}

			context.write(key, new LongWritable(count));

		}

	}

	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);

		job.setJarByClass(WordCount.class);

		// job.setMapperClass(MultiMapperA.class);
		MultipleInputs.addInputPath(job, new Path("c:/wordcount/textdata"), TextInputFormat.class, TextMapperA.class);
		MultipleInputs.addInputPath(job, new Path("c:/wordcount/seqdata"), SequenceFileInputFormat.class, SequenceMapperB.class);

		job.setReducerClass(SameReducer.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);

		// FileInputFormat.setInputPaths(job, new Path("c:/wordcount/data"));
		FileOutputFormat.setOutputPath(job, new Path("c:/wordcount/multiinouts"));

		job.waitForCompletion(true);

	}

}
