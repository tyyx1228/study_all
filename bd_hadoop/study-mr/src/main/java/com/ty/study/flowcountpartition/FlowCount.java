package com.ty.study.flowcountpartition;

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

	static class FlowCountMapper extends Mapper<LongWritable, Text, Text, FlowBean> {

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			String line = value.toString();
//			String[] fields = StringUtils.split(line, '\t');
			String[] fields = line.split("\t");
			try {
				String phonenbr = fields[1];

				long upflow = Long.parseLong(fields[fields.length - 3]);
				long dflow = Long.parseLong(fields[fields.length - 2]);

				FlowBean flowBean = new FlowBean(upflow, dflow);

				context.write(new Text(phonenbr), flowBean);
			} catch (Exception e) {

				e.printStackTrace();
			}

		}

	}

	static class FlowCountReducer extends Reducer<Text, FlowBean, Text, FlowBean> {

		@Override
		protected void reduce(Text key, Iterable<FlowBean> beans, Context context) throws IOException, InterruptedException {

			long upflow_sum = 0;
			long downflow_sum = 0;

			for (FlowBean bean : beans) {

				upflow_sum += bean.getUpflow();
				downflow_sum += bean.getDownflow();

			}

			FlowBean flowBean = new FlowBean(upflow_sum, downflow_sum);

			context.write(key, flowBean);

		}

	}

	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();

		Job job = Job.getInstance(conf);

		job.setJarByClass(FlowCount.class);

		job.setMapperClass(FlowCountMapper.class);
		job.setReducerClass(FlowCountReducer.class);

		// job.setMapOutputKeyClass(Text.class);
		// job.setMapOutputValueClass(FlowBean.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FlowBean.class);

		// job.setInputFormatClass(TextInputFormat.class);

		//在job中指定自定义的partitioner组件
		job.setPartitionerClass(ProvincePartitioner.class);
		//设置相应的reduce task数量
		job.setNumReduceTasks(8);

		FileInputFormat.setInputPaths(job, new Path("c:/wordcount/flowinput"));
		FileOutputFormat.setOutputPath(job, new Path("c:/wordcount/flowprovince"));

		job.waitForCompletion(true);

	}

}

