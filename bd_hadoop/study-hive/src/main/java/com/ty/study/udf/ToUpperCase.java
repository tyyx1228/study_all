package com.ty.study.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

public class ToUpperCase extends UDF{

	public String evaluate(String name) {
		return name.toUpperCase();

	}
	
	
	
}
