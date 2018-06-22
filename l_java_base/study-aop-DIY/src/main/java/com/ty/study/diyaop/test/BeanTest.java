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
		Object o = new BeanFactory(in).getBean("bean");
		StudentDAO stuDAO = (StudentDAO) o;
		stuDAO.insert();
		for(Method m :o.getClass().getMethods()){
			System.out.println(m);
		}
	}
}
