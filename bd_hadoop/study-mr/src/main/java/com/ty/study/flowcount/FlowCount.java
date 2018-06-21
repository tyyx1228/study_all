package com.ty.study.flowcount;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FlowCount {

	static class FlowCountMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
	 
		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			Counter counter = context.getCounter("zu-ziduanbudui", "ziduanshaole");
			String line = value.toString();
			// String[] fields = StringUtils.split(line, '\t');
			String[] fields = line.split("\t");
			try {
				String phonenbr = fields[1];

				long upflow = Long.parseLong(fields[fields.length - 3]);
				long dflow = Long.parseLong(fields[fields.length - 2]);

				FlowBean flowBean = new FlowBean(upflow, dflow);

				context.write(new Text(phonenbr), flowBean);
				
				
			} catch (Exception e) {
				counter.increment(1);
				e.printStackTrace();
			}

		}

	}

	static class FlowCountReducer extends Reducer<Text, FlowBean, Text, FlowBean> {
		

		@Override
		protected void reduce(Text key, Iterable<FlowBean> beans, Context context) throws IOException, InterruptedException {

			Iterator<FlowBean> it = beans.iterator();
			long upflow_sum = 0;
			long downflow_sum = 0;

			 for (FlowBean bean : beans) {
				 upflow_sum += bean.getUpflow();
				 downflow_sum += bean.getDownflow();
				 
			 }

			// for (FlowBean bean : beans) {
			// FlowBean newBean = new FlowBean();
			// try {
			// BeanUtils.copyProperties(newBean, bean);
			// } catch (Exception e) {
			// }
			// list.add(newBean);
			// }

			FlowBean flowBean = new FlowBean(upflow_sum, downflow_sum);

			context.write(key, flowBean);

		}

	}

	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();

		/*
		 * conf.set("fs.defaultFS", "hdfs://hadoop01:9000");
		 * conf.set("mapreduce.framework.name", "yarn");
		 * conf.set("yarn.resourcemanager.hostname", "hadoop01");
		 * System.setProperty("HADOOP_USER_NAME", "hadoop");
		 */

		Job job = Job.getInstance(conf);

		job.setJarByClass(FlowCount.class);
		// job.setJar("C:/home/work-program/workspace/mapreduce-demos/wc.jar");

		job.setMapperClass(FlowCountMapper.class);
		job.setReducerClass(FlowCountReducer.class);

		// job.setMapOutputKeyClass(Text.class);
		// job.setMapOutputValueClass(FlowBean.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FlowBean.class);

		// job.setInputFormatClass(TextInputFormat.class);

		FileInputFormat.setInputPaths(job, new Path("X:\\wordcount\\flowinput"));
		FileSystem hdfs = FileSystem.get(conf);
		Path output = new Path("X:\\wordcount\\flowout1");
		if (hdfs.exists(output))
			hdfs.delete(output, true);
		FileOutputFormat.setOutputPath(job, output);

		// FileInputFormat.setInputPaths(job, new Path(args[0]));
		// FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setNumReduceTasks(1);

		job.waitForCompletion(true);
		
		
		

	}

}
