package com.ty.study.proxy.impl;

import com.ty.study.proxy.Animal;

public class Dog implements Animal {

	@Override
	public void eat() {
		System.out.println("dog eating");
	}

	/**
	 * 导盲
	 */
	public void guide(){
		System.out.println("guide the way");
	}

}
