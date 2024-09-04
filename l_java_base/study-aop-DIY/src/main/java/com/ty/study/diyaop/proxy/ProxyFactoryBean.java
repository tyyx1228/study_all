package com.ty.study.diyaop.proxy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 *@author tongyu
 *@time 2015年2月7日 下午1:46:15
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProxyFactoryBean<T> {

	private T target;
	private Advice advice;
	/**
	 * 获得代理类
	 * @return
	 */
	public T getProxy(){

		T obj = (T) Proxy.newProxyInstance(
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
