package com.ty.study.innerReflect.userpackage;

import com.ty.study.innerReflect.framework.AInterceptorInterface;

public class CustomAInterceptor implements AInterceptorInterface{

	public String a;
	public CustomAInterceptor(String a){
		this.a = a;
	}
	
	@Override
	public void intercept() {
		 System.out.println(a);
		
	}
	
	
	public static class Builder implements AInterceptorInterface.Builder{

		@Override
		public AInterceptorInterface build(String a) {
			 
			return new CustomAInterceptor(a);
		}
		
	}
	
	

}
