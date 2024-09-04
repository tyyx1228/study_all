package com.ty.study.diyaop.factory;

import com.ty.study.diyaop.proxy.Advice;
import com.ty.study.diyaop.proxy.ProxyFactoryBean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *造出bean，或者代理对象
 *@author tongyu
 *@time 2015年2月7日 下午1:34:15
 */

public class BeanFactory {

	private Properties prop = new Properties();

	/**
	 * 用流对象造出本类实例
	 * @param inStream
	 */
	public BeanFactory(InputStream inStream){
		try {
			prop.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据Properties中的类的别名实例出name对应的对象或者代理对象
	 * @param name
	 * @return
	 */
	public <T> T getBean(String name){
		try {
			String className = prop.getProperty(name);
			Class<?> clazz = Class.forName(className);
			Object bean = clazz.newInstance();

			className = prop.getProperty(name+".advice");
			Advice advice = (Advice) Class.forName(className).newInstance();

			advice.setBeforeMessage(prop.getProperty(name+".beforMessage"));
			advice.setAfterMessage(prop.getProperty(name+".afterMessage"));

			ProxyFactoryBean<T> proxyBean = new ProxyFactoryBean(bean, advice);

			return proxyBean.getProxy();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


}
