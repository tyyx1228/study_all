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
/**
 * 步骤一：
 * 在map中将单词切出来后发出：<单词-文档,1>
 * 在reduce中就可以统计特定单词在特定文档中出现的总次数 <hello-a.txt,3>
 hello--a.txt	3
 hello--b.txt	2
 hello--c.txt	2
 jerry--a.txt	1
 jerry--b.txt	3
 jerry--c.txt	1
 tom--a.txt	2
 tom--b.txt	1
 tom--c.txt	1
 *
 *
 * @author
 *
 */

public class IndexStepOne {

	static class IndexStepOneMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			String line = value.toString();

			String[] words = line.split(" ");

			// 这个对象里面包含了本次处理的这一行数据所在的文件的信息
			FileSplit fileSplit = (FileSplit) context.getInputSplit();
			String fileName = fileSplit.getPath().getName();
			for (String word : words) {
				context.write(new Text(word + "--" + fileName), new LongWritable(1));
			}
		}

	}

	static class IndexStepOneReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

		@Override
		protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {

			long count = 0;
			for (LongWritable value : values) {

				count += value.get();
			}

			context.write(key, new LongWritable(count));

		}

	}

	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();

		Job job = Job.getInstance(conf);
		job.setJarByClass(IndexStepOne.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);

		FileInputFormat.setInputPaths(job, new Path("c:/wordcount/inverindexinput"));
		FileOutputFormat.setOutputPath(job, new Path("c:/wordcount/inverindexoutput-step1"));
//		FileInputFormat.setInputPaths(job, new Path(args[0]));
//		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setMapperClass(IndexStepOneMapper.class);
		job.setReducerClass(IndexStepOneReducer.class);

		job.waitForCompletion(true);



	}

}

