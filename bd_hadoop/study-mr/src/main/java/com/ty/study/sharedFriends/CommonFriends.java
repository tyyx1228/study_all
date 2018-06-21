package com.ty.study.sharedFriends;

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
/**
 * 第一个步骤就是为了求得，每一个人，都是哪些人的共同好友
 * 
朋友    人-人-人-人.....
A	I-K-C-B-G-F-H-O-D-
B	A-F-J-E-
C	A-E-B-H-F-G-K-
D	G-C-K-A-L-F-E-H-
E	G-M-L-H-A-F-B-D-
 * 
 * 
 * @author
 *
 */

public class CommonFriends {

	static class CommonFriendsMapper extends Mapper<LongWritable, Text, Text, Text> {

		// A:B,C,D,F,E,O B-A C-A D-A F-A E-A O-A
		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			String line = value.toString();

			String[] split = line.split(":");
			String person = split[0];
			String[] friends = split[1].split(",");

			for (String f : friends) {

				context.write(new Text(f), new Text(person));

			}

		}

	}

	static class CommonFriendsReducer extends Reducer<Text, Text, Text, Text>{
		
		//B {a,e,f,j}
		@Override
		protected void reduce(Text f, Iterable<Text> persons, Context context) throws IOException, InterruptedException {
			StringBuilder sb = new StringBuilder();
			for(Text p:persons){
				sb.append(p+"-");
			}
			//B A-E-F-J
			context.write(f, new Text(sb.toString()));
			
		}
		
	}
	
	public static void main(String[] args) throws Exception {


		Configuration conf = new Configuration();

		Job job = Job.getInstance(conf);

		job.setJarByClass(CommonFriends.class);

		job.setMapperClass(CommonFriendsMapper.class);
		job.setReducerClass(CommonFriendsReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);


		FileInputFormat.setInputPaths(job, new Path("C:\\wordcount\\friends"));
		FileOutputFormat.setOutputPath(job, new Path("c:/wordcount/friends-step1"));

		job.waitForCompletion(true);

	
	}
}
