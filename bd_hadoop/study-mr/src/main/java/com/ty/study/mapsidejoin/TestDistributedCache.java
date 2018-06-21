package com.ty.study.mapsidejoin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class TestDistributedCache {
	static class TestDistributedCacheMapper extends Mapper<LongWritable, Text, Text, Text>{
		FileReader in = null;
		BufferedReader reader = null;
		HashMap<String,String> b_tab = new HashMap<String, String>();
		String localpath =null;
		String uirpath = null;
		
		//是在maptask任务初始化的时候调用一次
		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			//通过这几句代码可以获取到cache file的本地绝对路径，测试验证用
			/*Path[] files = context.getLocalCacheFiles();
			localpath = files[0].toString();
			URI[] cacheFiles = context.getCacheFiles();*/
			
			
			//缓存文件的用法——直接用本地IO来读取
			//这里读的数据是map task所在机器本地工作目录中的一个小文件
			in = new FileReader("pdts.txt");
			reader =new BufferedReader(in);
			String line =null;
			while(null!=(line=reader.readLine())){
				
				String[] fields = line.split(",");
				b_tab.put(fields[0],fields[1]);
				
			}
			IOUtils.closeStream(reader);
			IOUtils.closeStream(in);
			
		}
		
		
		
		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			 String[] fields = value.toString().split("\t");
			 
			 String a_itemid = fields[0];
			 String a_amount = fields[1];
			 
			 String b_name = b_tab.get(a_itemid);
			 
			 // 输出结果  1001	98.9	banana
			 context.write(new Text(a_itemid), new Text(a_amount + "\t" + ":" + localpath + "\t" +b_name ));
			 
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
		job.setJarByClass(TestDistributedCache.class);
		
		job.setMapperClass(TestDistributedCacheMapper.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		
		//这里是我们正常的需要处理的数据所在路径
		FileInputFormat.setInputPaths(job, new Path("x:/wordcount/mapjoininput"));
		FileOutputFormat.setOutputPath(job, new Path("x:/wordcount/mapjoinoutput9"));
//		FileInputFormat.setInputPaths(job, new Path(args[0]));
//		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		//不需要reducer
		job.setNumReduceTasks(0);
		//分发一个文件到task进程的工作目录
		job.addCacheFile(new URI("file:///x:/wordcount/mapjoincache/pdts.txt"));
//		job.addCacheFile(new URI("hdfs://hadoop-server01:9000/cachefile/b.txt"));
		
		//分发一个归档文件到task进程的工作目录
//		job.addArchiveToClassPath(archive);

		//分发jar包到task节点的classpath下
//		job.addFileToClassPath(jarfile);
		
		job.waitForCompletion(true);
	}

}
