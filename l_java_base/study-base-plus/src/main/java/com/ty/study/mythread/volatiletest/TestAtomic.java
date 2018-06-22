package com.ty.study.mythread.volatiletest;

import java.util.concurrent.atomic.AtomicInteger;

public class TestAtomic {

	public static  AtomicInteger numb = new AtomicInteger(0);

	public static void main(String[] args) throws Exception {

		for (int i = 0; i < 100; i++) {

			new Thread(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < 1000; i++) {
						numb.incrementAndGet();
					}
				}
			}).start();

		}
		
		Thread.sleep(2000);
		System.out.println(numb);
	}

}
