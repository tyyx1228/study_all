package com.ty.study.sharedFriends;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 
输入数据：是上一个步骤的输出结果
好友    人-人-人-人.....
A	I-K-C-B-G-F-H-O-D-
B	A-F-J-E-
C	A-E-B-H-F-G-K-
D	G-C-K-A-L-F-E-H-
E	G-M-L-H-A-F-B-D-

第二个步骤的输入数据是上一个步骤的输出，如上

本步骤要做的事，就是把有共同好友的人，两两配对作为key，好友作为value发出去，以便在reduce中能拿到一个两两对的所有好友

 * 
 * @author
 *
 */


public class CommonFriendsStepTwo {

	static class CommonFriendsStepTwoMapper extends Mapper<LongWritable, Text, Text, Text> {

		// a	b-d-f-g-h-i-k-o-     BD-A  BF-A  BG-A BH-A BI-A BK-A BO-A DF-A DG-A
		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			String line = value.toString();
			String[] split = line.split("\t");
			String friend = split[0];
			String[] persons = split[1].split("-");
			
			Arrays.sort(persons);
			
			for(int i=0;i<persons.length-1;i++){
				for(int j=i+1;j<persons.length;j++){
					
					context.write(new Text(persons[i]+"-"+persons[j]), new Text(friend));
				}
				
				
			}
			
		}

	}

	static class CommonFriendsStepTwoReducer extends Reducer<Text, Text, Text, Text>{
		
		//BD-A BD-C BD-G
		@Override
		protected void reduce(Text pair, Iterable<Text> friends, Context context) throws IOException, InterruptedException {
			StringBuffer sb = new StringBuffer();
			for(Text f:friends){
				
				sb.append(f+ ",");
			}
			context.write(pair, new Text(sb.toString()));
			
		}
		
	}
	
	public static void main(String[] args) throws Exception {


		Configuration conf = new Configuration();

		Job job = Job.getInstance(conf);

		job.setJarByClass(CommonFriendsStepTwo.class);

		job.setMapperClass(CommonFriendsStepTwoMapper.class);
		job.setReducerClass(CommonFriendsStepTwoReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);


		FileInputFormat.setInputPaths(job, new Path("C:\\wordcount\\friends-step1"));
		FileOutputFormat.setOutputPath(job, new Path("C:\\wordcount\\friends-step2"));

		job.waitForCompletion(true);

	
	}
}
