package com.ty.study.interceptor;

import com.ty.study.interceptor.B.INB;

public class A {
	public static void main(String[] args) throws Exception {

		Class<?> forName = Class.forName("com.ty.study.interceptor.B$INB");
		B.INB inb = (INB) forName.newInstance();
		BInterface build = inb.build();
		build.intercept();
	}

}
