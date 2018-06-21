package com.ty.study.innerReflect.framework;

public interface AInterceptorInterface {

	public void intercept();
	
	public interface Builder{
		
		public AInterceptorInterface build(String a); 
	}
	
}
