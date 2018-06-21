package com.ty.study.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HdfsDemo {

	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();
		conf.set("fs.defaultFS","hdfs://hdp-node-01:9000");
		System.setProperty("HADOOP_USER_NAME", "hadoop");
		
		FileSystem fs = FileSystem.get(conf);
		
//		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop01:9000"), conf, "hadoop");
		
		/*fs.copyFromLocalFile(new Path("c:/111.txt"), new Path("/111.txt"));*/

		fs.copyToLocalFile(new Path("/111.txt"), new Path("d:/222.txt"));
		
		//参数4：是否使用原生的java操作本地文件系统;如果为false，则使用winutils；如果为true，则用java操作
//		fs.copyToLocalFile(false, new Path("/111.txt"), new Path("c:/233.txt"), true);
		
		
		fs.close();
		
	}

}
