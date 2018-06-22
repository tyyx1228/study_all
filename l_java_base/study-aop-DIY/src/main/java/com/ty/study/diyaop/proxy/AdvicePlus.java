package com.ty.study.diyaop.proxy;

public class AdvicePlus extends Advice {
	@Override
	public void beforeMethod() {
		// TODO Auto-generated method stub
		//super.beforeMethod();
		//beforePlus();
	}
	private void beforePlus(){
		System.out.println("beforeMethod");
	}

}
