package com.ty.study.mapsidejoin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MapJoinDistributedCacheFile {
	private static final Log log = LogFactory.getLog(MapJoinDistributedCacheFile.class);
	public static class MapJoinDistributedCacheFileMapper extends Mapper<LongWritable, Text, Text, NullWritable>{
	
		FileReader in = null;
		BufferedReader reader = null;
		HashMap<String,String[]> b_tab = new HashMap<String, String[]>();
		String localpath =null;
		String uirpath = null;
		@Override
		protected void setup(Context context)throws IOException, InterruptedException {
			// 此处加载的是产品表的数据
			in = new FileReader("pdts.txt");
			reader = new BufferedReader(in);
			String line =null;
			while(StringUtils.isNotBlank((line=reader.readLine()))){
				String[] split = line.split(",");
				String[] products = {split[0],split[1]};
				b_tab.put(split[0], products);
			}
			IOUtils.closeStream(reader);
			IOUtils.closeStream(in);
		}
		
		@Override
		protected void map(LongWritable key, Text value,Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] orderFields = line.split("\t");
			String pdt_id = orderFields[1];
			String[] pdtFields = b_tab.get(pdt_id);
			String ll = pdtFields[0] + "\t" + pdtFields[1] + "\t" + orderFields[1] + "\t" + orderFields[2] ;
			context.write(new Text(ll), NullWritable.get());
		}
	}
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
		job.setJarByClass(MapJoinDistributedCacheFile.class);
		job.setMapperClass(MapJoinDistributedCacheFileMapper.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path("X:/wordcount/mapjoininput"));
		FileOutputFormat.setOutputPath(job, new Path("x:/wordcount/mapjoinout6"));
//		FileInputFormat.setInputPaths(job, new Path(args[0]));
//		FileOutputFormat.setOutputPath(job, new Path(args[1]));
			
		job.setNumReduceTasks(0);
		
//		job.addCacheFile(new URI("file:/c:/mjoin/pdts.txt"));
		job.addCacheFile(new URI("file:/C:/home/work-program/workspace/example-mapreduce/wordcount/mapjoincache/pdts.txt"));
//		job.addCacheFile(new URI("hdfs://hadoop01:9000/cachefile/joinProduct.txt"));
		
		
		job.waitForCompletion(true);
	}
}
