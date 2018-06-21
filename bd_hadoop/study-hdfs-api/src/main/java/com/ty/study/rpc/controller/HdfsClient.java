package com.ty.study.rpc.controller;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.ty.study.rpc.ClientNameNodeProtocol;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

public class HdfsClient {
	
	public static void main(String[] args) throws IOException {
		
		ClientNameNodeProtocol namenode = RPC.getProxy(ClientNameNodeProtocol.class, 100L, new InetSocketAddress("hadoop01", 1314), new Configuration());
		
		String metaData = namenode.getMetaData("/苍老师初解禁.avi");
		
		System.out.println(metaData);
		
		
		
		
	}
	
	

}
