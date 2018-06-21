package com.ty.study.interceptor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class TestUnicode {
	
	public static String UnicodeToString(String str) {
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		return str;
	}

	@Test
	public void test(){
		
		String unicodeToString = UnicodeToString("\u0003");
		System.out.println(unicodeToString);
	}
	
}
