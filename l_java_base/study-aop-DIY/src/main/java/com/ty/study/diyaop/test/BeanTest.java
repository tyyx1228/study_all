package com.ty.study.diyaop.test;

import com.ty.study.diyaop.dao.StudentDAO;
import com.ty.study.diyaop.factory.BeanFactory;

import java.io.InputStream;
import java.lang.reflect.Method;

/**
 *
 *@author tongyu
 *@time 2015年2月7日 下午2:18:33
 */

public class BeanTest {


	public static void main(String[] args) {

		InputStream in = BeanTest.class.getClassLoader().getResourceAsStream("config.properties");
		BeanFactory beanFactory = new BeanFactory(in);
		StudentDAO stuDAO = beanFactory.getBean("bean");
		stuDAO.insert();
		for(Method m : stuDAO.getClass().getMethods()){
			System.out.println(m);
		}
	}
}
