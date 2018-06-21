package com.ty.study.hdfs;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Test {
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		
		conf.set("fs.defaultFS", "hdfs://mini1:9000");
		FileSystem fs = FileSystem.get(conf);
		
		
		FSDataOutputStream out = fs.create(new Path("/aaa"));
		
		FileInputStream in = new FileInputStream("F:/installpkg/jdk-8u60-linux-x64.gz");
		
		IOUtils.copy(in, out);
		
		
	}

}
