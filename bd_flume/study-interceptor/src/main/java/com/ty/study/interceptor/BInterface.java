package com.ty.study.interceptor;

public abstract interface BInterface {
	
	public void intercept();
	
	
	public abstract interface BIN{
		public BInterface build() ;
	}

}
