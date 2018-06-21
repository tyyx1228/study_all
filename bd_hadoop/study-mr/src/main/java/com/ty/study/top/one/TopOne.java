package com.ty.study.top.one;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.sun.xml.bind.v2.schemagen.xmlschema.List;

/**
 * 利用secondarysort机制输出每种item订单金额最大的记录
 * 
 * @author duanhaitao@itcast.cn
 * 
 */
public class TopOne {

	static class TopOneMapper extends Mapper<LongWritable, Text, OrderBean, NullWritable> {

		OrderBean bean = new OrderBean();

		/* Text itemid = new Text(); */

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			String line = value.toString();
			String[] fields = StringUtils.split(line, ",");

			bean.set(new Text(fields[0]), new DoubleWritable(Double.parseDouble(fields[2])));

			context.write(bean, NullWritable.get());

		}

	}

	static class TopOneReducer extends Reducer<OrderBean, NullWritable, OrderBean, NullWritable> {

		// 在设置了groupingcomparator以后，这里收到的kv数据 就是： <1001 87.6>,null <1001
		// 76.5>,null ....
		// 此时，reduce方法中的参数key就是上述kv组中的第一个kv的key：<1001 87.6>
		// 要输出同一个item的所有订单中最大金额的那一个，就只要输出这个key
		@Override
		protected void reduce(OrderBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
			context.write(key, NullWritable.get());
		}
	}

	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);

		job.setJarByClass(TopOne.class);

		job.setMapperClass(TopOneMapper.class);
		job.setReducerClass(TopOneReducer.class);

		job.setOutputKeyClass(OrderBean.class);
		job.setOutputValueClass(NullWritable.class);

		FileInputFormat.setInputPaths(job, new Path("x:/wordcount/gpinput"));
		FileOutputFormat.setOutputPath(job, new Path("x:/wordcount/gpoutput3"));
		// FileInputFormat.setInputPaths(job, new Path(args[0]));
		// FileOutputFormat.setOutputPath(job, new Path(args[1]));
		// 指定shuffle所使用的GroupingComparator类
		job.setGroupingComparatorClass(ItemidGroupingComparator.class);
		// 指定shuffle所使用的partitioner类
		job.setPartitionerClass(ItemIdPartitioner.class);

		job.setNumReduceTasks(1);

		job.waitForCompletion(true);

	}

}
