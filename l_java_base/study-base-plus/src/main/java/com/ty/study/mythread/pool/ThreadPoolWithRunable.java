package com.ty.study.mythread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolWithRunable {

	
	/**
	 * 通过线程池执行线程
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		//创建一个线程池
		ExecutorService pool = Executors.newCachedThreadPool();
		for(int i = 1; i < 50; i++){
			pool.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println("thread name: " + Thread.currentThread().getName());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		Thread.sleep(10000);
		
		
		for(int i = 1; i < 50; i++){
			pool.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println("thread name: " + Thread.currentThread().getName());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		
		pool.shutdown();
	}

}
