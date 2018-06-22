package com.ty.study.proxy;


import com.ty.study.proxy.factory.ProxyFactory;
import com.ty.study.proxy.impl.Dog;
import com.ty.study.proxy.exception.ObjectUnenableProxyException;

public class TestCglib {

	public static void main(String[] args) {
		testJDKProxy();
		testCglib();

	}
	
	private static void testCglib(){
        Dog tp = ProxyFactory.getProxyByCglib(new Dog());
        tp.eat();
        tp.guide();
	}

	private static void testJDKProxy() {
		final Dog t = new Dog();
		Animal proxy = null;
		try {
			proxy = ProxyFactory.getProxyByJdk(t, Animal.class);
		} catch (ObjectUnenableProxyException e) {
			e.printStackTrace();
		}
		System.out.println("proxy isinstanceof Dog: " + (proxy instanceof Dog));
		proxy.eat();
	}
	

}
