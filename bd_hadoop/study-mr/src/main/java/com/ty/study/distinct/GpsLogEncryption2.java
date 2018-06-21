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


public class GpsLogEncryption2 {

	static class GpsLogEncryptionMapper extends Mapper<LongWritable, Text, GpsLogBean, NullWritable> {

		GpsLogBean gpsLogBean = new GpsLogBean();

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
			context.write(gpsLogBean, NullWritable.get());
		}
	}

	static class GpsLogEncryptionReducer extends Reducer<GpsLogBean, NullWritable, GpsLogBean, NullWritable> {
		@Override
		protected void reduce(GpsLogBean bean, Iterable<NullWritable> value, Context context)
				throws IOException, InterruptedException {
			context.write(bean, NullWritable.get());
		}
	}

	public static void main(String[] args) {
		Configuration conf = new Configuration();
		try {
			Job job = Job.getInstance(conf);

			job.setJarByClass(GpsLogEncryption2.class);

			job.setMapperClass(GpsLogEncryptionMapper.class);
			job.setReducerClass(GpsLogEncryptionReducer.class);

			job.setOutputKeyClass(GpsLogBean.class);
			job.setOutputValueClass(NullWritable.class);

//			FileInputFormat.setInputPaths(job, new Path("D:\\highway-data\\gpslog_20151201"));
//			FileOutputFormat.setOutputPath(job, new Path("D:\\wordcount\\output\\gpslog_20151201_out3"));
			FileInputFormat.setInputPaths(job, new Path("F:\\bigdata-material-data\\distinct-test\\input"));
			FileOutputFormat.setOutputPath(job, new Path("F:\\bigdata-material-data\\distinct-test\\output2"));

			job.waitForCompletion(true);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
