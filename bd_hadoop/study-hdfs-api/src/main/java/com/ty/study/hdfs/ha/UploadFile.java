package com.ty.study.hdfs.ha;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


/**
 * 如果访问的是一个ha机制的集群
 * 则一定要把core-site.xml和hdfs-site.xml配置文件放在客户端程序的classpath下
 * 以让客户端能够理解hdfs://ns1/中  “ns1”是一个ha机制中的namenode对——nameservice
 * 以及知道ns1下具体的namenode通信地址
 * @author
 *
 */
public class UploadFile {
	
	public static void main(String[] args) throws Exception  {
		
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://ns1/"),conf,"root");
		
		fs.copyFromLocalFile(new Path("F:/installpkg/cenos-6.5-hadoop-2.6.4.tar.gz"), new Path("hdfs://ns1/"));
		
		fs.close();
		    
	}
	

}
