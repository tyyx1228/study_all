package com.ty.study.innerReflect.framework;

import com.ty.study.innerReflect.framework.AInterceptorInterface.Builder;

public class ServiceTest {
	
	public static void main(String[] args) throws Exception {
		
		String customBuilderName = "com.ty.study.innerReflect.userpackage.CustomAInterceptor$Builder";
		Class<?> forName = Class.forName(customBuilderName);
		
		AInterceptorInterface.Builder builder = (Builder) forName.newInstance();
		AInterceptorInterface interceptor = builder.build("haha");
		interceptor.intercept();
		
	}

}
