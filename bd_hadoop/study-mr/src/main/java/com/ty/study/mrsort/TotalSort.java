package com.ty.study.mrsort;

import java.io.IOException;
import java.net.URI;

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
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.InputSampler;
import org.apache.hadoop.mapreduce.lib.partition.InputSampler.RandomSampler;
import org.apache.hadoop.mapreduce.lib.partition.TotalOrderPartitioner;

import com.ty.study.mrjson.ResultBean;
/**
 * 全排序示例
 * @author duanhaitao@itcast.cn
 *
 */
public class TotalSort {

	static class TotalSortMapper extends Mapper<ResultBean, NullWritable, ResultBean, NullWritable> {
		ResultBean bean = new ResultBean();

		@Override
		protected void map(ResultBean key, NullWritable value, Context context) throws IOException, InterruptedException {

			/*String line = value.toString();
			String[] fields = line.split("\t");
			bean.set(Long.parseLong(fields[0]), Long.parseLong(fields[1]));*/

			context.write(key, NullWritable.get());

		}

	}

	static class TotalSortReducer extends Reducer<ResultBean, NullWritable, ResultBean, NullWritable> {

		@Override
		protected void reduce(ResultBean bean, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {

			context.write(bean, NullWritable.get());
			/*for (ResultBean v : values) {
				context.write(v, NullWritable.get());
			}*/
		}
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);

		job.setJarByClass(TotalSort.class);

		job.setMapperClass(TotalSortMapper.class);
		job.setReducerClass(TotalSortReducer.class);
//
		job.setMapOutputKeyClass(ResultBean.class);
		job.setMapOutputValueClass(NullWritable.class);
		job.setOutputKeyClass(ResultBean.class);
		job.setOutputValueClass(NullWritable.class);

		//用来读取sequence源文件的输入组件
		job.setInputFormatClass(SequenceFileInputFormat.class);


		FileInputFormat.setInputPaths(job, new Path("X:\\wordcount\\jsonoutput-seq"));
		FileOutputFormat.setOutputPath(job, new Path("X:\\wordcount\\jsonoutput-sort"));


		//设置若干并发的reduce task
		job.setNumReduceTasks(6);


//		job.setPartitionerClass(RangePartitioner.class);
		//分区的逻辑使用的hadoop自带的全局排序分区组件
		job.setPartitionerClass(TotalOrderPartitioner.class);

		//系统自带的这个抽样器只能针对sequencefile抽样
		RandomSampler<LongWritable, ResultBean> randomSampler = new InputSampler.RandomSampler<LongWritable,ResultBean>(0.1,10);
		InputSampler.writePartitionFile(job, randomSampler);

		//获取抽样器所产生的分区规划描述文件
		Configuration conf2 = job.getConfiguration();
		String partitionFile = TotalOrderPartitioner.getPartitionFile(conf2);

		//把分区描述规划文件分发到每一个task节点的本地
		job.addCacheFile(new URI(partitionFile));



		job.waitForCompletion(true);

	}

}
