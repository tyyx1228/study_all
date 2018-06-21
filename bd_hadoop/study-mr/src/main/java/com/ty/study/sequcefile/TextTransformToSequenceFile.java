package com.ty.study.sequcefile;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

public class TextTransformToSequenceFile {

	static class TextTransformToSequenceFileMapper extends Mapper<LongWritable, Text, Text, Text> {

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			String[] fields = value.toString().split("\t");
			try {
				String phonenbr = fields[6];
				String domain = fields[25];
				String clienttype = fields[27];
				String upflow = fields[30];
				String dflow = fields[31];

				context.write(new Text(phonenbr), new Text(domain + "\t" + clienttype + "\t" + upflow + "\t" + dflow));
			} catch (Exception e) {

			}
		}

	}

	static class TextTransformToSequenceFileReducer extends Reducer<Text, Text, Text, Text> {

		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			for (Text v : values) {

				context.write(key, v);

			}

		}

	}

	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();

		Job job = Job.getInstance(conf);

		job.setJarByClass(TextTransformToSequenceFile.class);

		job.setMapperClass(TextTransformToSequenceFileMapper.class);
		job.setReducerClass(TextTransformToSequenceFileReducer.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		// ָ��������ΪSequenceFileOutputFormat��������Ľ���ļ�ΪSequenceFile����
		// job.setOutputFormatClass(SequenceFileOutputFormat.class);

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.waitForCompletion(true);

	}

}
