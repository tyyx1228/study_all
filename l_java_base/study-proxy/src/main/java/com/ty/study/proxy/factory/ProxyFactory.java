package com.ty.study.proxy.factory;


import com.ty.study.proxy.exception.ObjectUnenableProxyException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理工厂用于获取代理类
 *
 * @author relax tongyu
 * @create 2018-02-06 14:17
 **/
public class ProxyFactory {

    private static CglibProxy cp;

    private static CglibProxy getCglibProxy(){
        if(cp==null){
            cp = new CglibProxy();
        }
        return cp;
    }

    public static <T> T getProxyByCglib(T obj){
        return getCglibProxy().getInstance(obj);
    }

    public static <T> T getProxyByJdk(final Object obj, Class<T> type)
            throws ObjectUnenableProxyException {
        Class<?> clazz = obj.getClass();
        Class<?>[] interfaces = clazz.getInterfaces();
        if(interfaces.length<1){
            throw new ObjectUnenableProxyException(clazz.getName() + " 没有实现任何接口，无法代理");
        }

        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                interfaces,
                new InvocationHandler() {
                    private void beforeInvokeMethod(){}

                    private void afterInvokeMethod(){}

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args)
                            throws Throwable {
                        beforeInvokeMethod();
                        Object res = method.invoke(obj, args);
                        afterInvokeMethod();
                        return res;
                    }
                });
    }

}
