package com.ty.study.reflect.socket;

public class TestBusiness implements IBusiness{
	@Override
	public int getPrice(String good){
		return good.equals("yifu")?10:20;
	}
}
