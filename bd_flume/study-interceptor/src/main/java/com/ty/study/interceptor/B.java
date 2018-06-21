package com.ty.study.interceptor;


public class B implements BInterface{
	private String a;
	public B(String a){
		this.a = a;
	}
	
	class INB implements BInterface.BIN{
		
		private String a ;
		
		@Override
		public BInterface build() {
			System.out.println("haha");
			
			return new B("haha");
		}
	}

	@Override
	public void intercept() {
		System.out.println(a);
		
	}
	

}
