package com.ty.study.distinct;

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

public class GpsLogEncryption {

	static class GpsLogEncryptionMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

		GpsLogBean gpsLogBean = new GpsLogBean();
		Text k = new Text();

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			// String line = new String(value.getBytes(), "GBK");
			String line = value.toString();
			String[] fieds = line.split(" ");
			String phone_number = fieds[2].substring(7);
			String content = fieds[4];
			StringBuffer sb = new StringBuffer();
			sb.append(content.substring(0, content.lastIndexOf("|")));
			String carlicense = content.substring(content.lastIndexOf("|") + 1);
			String phone_encryption = GpsLogUtils.string2MD5(phone_number);
			String carlicense_encryption = GpsLogUtils.string2MD5(carlicense);
			sb.append("|").append(carlicense_encryption);
			gpsLogBean.setPhone_number(phone_encryption);
			gpsLogBean.setContent(sb.toString());
			k.set(gpsLogBean.toString());
			context.write(k, NullWritable.get());
		}
	}

	static class GpsLogEncryptionReducer extends Reducer<Text, NullWritable, Text, NullWritable> {
		@Override
		protected void reduce(Text key, Iterable<NullWritable> value, Context context) throws IOException, InterruptedException {
			context.write(key, NullWritable.get());
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		//conf.set("mapreduce.input.fileinputformat.split.maxsize", "16777216");
		//conf.set("mapreduce.input.fileinputformat.split.maxsize", "33554432");
		//conf.set("mapreduce.input.fileinputformat.split.maxsize", "67108864");
		//conf.set("mapreduce.map.memory.mb", "1024");
		//conf.set("mapreduce.reduce.memory.mb", "1024");
		//conf.set("yarn.app.mapreduce.am.resource.mb", "512");

		Job job = Job.getInstance(conf);

		job.setJarByClass(GpsLogEncryption.class);

		job.setMapperClass(GpsLogEncryptionMapper.class);
		job.setReducerClass(GpsLogEncryptionReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);

		// FileInputFormat.setInputPaths(job, new
		// Path("F:\\bigdata-material-data\\distinct-test\\input"));
		// FileOutputFormat.setOutputPath(job, new
		// Path("F:\\bigdata-material-data\\distinct-test\\output"));
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.waitForCompletion(true);

	}

}
