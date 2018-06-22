package com.ty.study.mythread.thread.lock;

public class Test {
	public static void main(String[] args) {
		//获取电脑CPU的内核数量
		int num = Runtime.getRuntime().availableProcessors();
		System.out.println(num);
	}

}
