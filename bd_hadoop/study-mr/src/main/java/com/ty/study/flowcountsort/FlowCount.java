package com.ty.study.flowcountsort;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FlowCount {

	static class FlowCountMapper extends Mapper<LongWritable, Text, FlowBean,Text > {

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			String line = value.toString();
			String[] fields = line.split("\t");
			try {
				String phonenbr = fields[0];

				long upflow = Long.parseLong(fields[1]);
				long dflow = Long.parseLong(fields[2]);

				FlowBean flowBean = new FlowBean(upflow, dflow);

				context.write(flowBean,new Text(phonenbr));
			} catch (Exception e) {

				e.printStackTrace();
			}

		}

	}

	static class FlowCountReducer extends Reducer<FlowBean,Text,Text, FlowBean> {

		@Override
		protected void reduce(FlowBean bean, Iterable<Text> phonenbr, Context context) throws IOException, InterruptedException {

			Text phoneNbr = phonenbr.iterator().next();

			context.write(phoneNbr, bean);

		}

	}

	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();

		Job job = Job.getInstance(conf);

		job.setJarByClass(FlowCount.class);

		job.setMapperClass(FlowCountMapper.class);
		job.setReducerClass(FlowCountReducer.class);

		 job.setMapOutputKeyClass(FlowBean.class);
		 job.setMapOutputValueClass(Text.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FlowBean.class);

		// job.setInputFormatClass(TextInputFormat.class);

//		FileInputFormat.setInputPaths(job, new Path(args[0]));
//		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		FileInputFormat.setInputPaths(job, new Path("x:/wordcount/flowout1"));
		FileOutputFormat.setOutputPath(job, new Path("x:/wordcount/flowout2"));
		
		job.setNumReduceTasks(1);
		
		job.waitForCompletion(true);

	}

}
