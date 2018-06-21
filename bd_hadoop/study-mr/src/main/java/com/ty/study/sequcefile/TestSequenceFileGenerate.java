package com.ty.study.sequcefile;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.hadoop.io.Text;

public class TestSequenceFileGenerate {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://hadoop-server01:9000/");
		FileSystem fs = FileSystem.get(conf);

		Writer createWriter = SequenceFile.createWriter(fs, conf, new Path("hdfs://hadoop-server01:9000/testseq.seq"), LongWritable.class, Text.class);

		for (int i = 0; i < 100; i++) {
			createWriter.append(new LongWritable(i), new Text("hello" + i));
		}
		createWriter.close();

	}

}
