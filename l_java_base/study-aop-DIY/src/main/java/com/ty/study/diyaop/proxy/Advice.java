package com.ty.study.diyaop.proxy;

/**
 *
 *@author tongyu
 *@time 2015年2月7日 下午1:52:29
 */

public class Advice {

	private String beforeMessage;
	private String afterMessage;

	public void beforeMethod(){
		System.out.println(getBeforeMessage());
	}
	public void afterMethod(){
		System.out.println(getAfterMessage());
	}
	public String getBeforeMessage() {
		return beforeMessage;
	}
	public void setBeforeMessage(String beforeMessage) {
		this.beforeMessage = beforeMessage;
	}
	public String getAfterMessage() {
		return afterMessage;
	}
	public void setAfterMessage(String afterMessage) {
		this.afterMessage = afterMessage;
	}
}
