package com.ty.study.proxy.factory;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor{

    private Enhancer enhancer;

    public synchronized <T> T getInstance(T target) {
        /**
         * Enhancer对象，官方说明为一个Enhancer对象可以代理多个类，但是线程之间不能共享（线程不安全）
         */
        if(enhancer == null){
            enhancer = new Enhancer();
        }
        enhancer.setSuperclass(target.getClass());
        // 回调方法
        enhancer.setCallback(this);
        // 创建代理对象
        return (T) enhancer.create();
    }


    // 回调方法
    @Override
    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        System.out.println("事物开始");
        proxy.invokeSuper(obj, args);
        System.out.println("事物结束");
        return null;


    }

}
