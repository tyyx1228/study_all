package com.ty.study.proxy.service.impl;

import com.ty.study.proxy.service.IBoss;


/**
 * 实现了卖衣服的接口
 * 自定义了自己的业务，卖裤子
 * @author coderblack
 *
 */
public class Boss implements IBoss{
	@Override
	public int yifu(String size){
		System.err.println("天猫小强旗舰店，老板给客户发快递----衣服型号："+size);
		//这件衣服的价钱，从数据库读取
		return 50;
	}
	public void kuzi(){
		System.err.println("天猫小强旗舰店，老板给客户发快递----裤子");
	}
	@Override
	public int haha() {
		// TODO Auto-generated method stub
		System.out.println("dkfldkfj");
		return 0;
	}
	
}
