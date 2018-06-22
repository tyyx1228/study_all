package com.ty.study.diyaop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 *@author tongyu
 *@time 2015年2月7日 下午1:46:15
 */

public class ProxyFactoryBean {
	/**
	 * 获得代理类
	 * @param target
	 * @param advice
	 * @return
	 */
	public Object getProxy(final Object target, final Advice advice){

		Object obj = Proxy.newProxyInstance(
				target.getClass().getClassLoader(),
				target.getClass().getInterfaces(),
				new InvocationHandler(){

					@Override
					public Object invoke(Object proxy, Method method,
										 Object[] args) throws Throwable {
						advice.beforeMethod();
						Object o = method.invoke(target, args);
						advice.afterMethod();
						return o;
					}
				}
		);

		return obj;
	}
}
