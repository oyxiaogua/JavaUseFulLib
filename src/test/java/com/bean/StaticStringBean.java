package com.bean;

public class StaticStringBean {
	public static final Object A = getB();
	//常量表达式指能立即得出的运算 包括普通运算以及字符串运算
	public static final String B = "String B"; 

	public static Object getB() {
		return B;
	}
}
