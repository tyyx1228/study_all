package com.ty.study.mythread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 列出并发包中的各种线程池
 * @author
 *
 */

public class ExecutorDemo {
	
	public static void main(String[] args) {
		// 创建单线程线程池
		ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
		// 创建可回收的线程池
		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
		
		int cpuNums = Runtime.getRuntime().availableProcessors();
		System.out.println(cpuNums);
		// 创建固定数量线程池
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(cpuNums);
		
		// 创建可调度的线程池
		ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(8);
		// 创建可调度的单线程 线程池	
		ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
	}
}
