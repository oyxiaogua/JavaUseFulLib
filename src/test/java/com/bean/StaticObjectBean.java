package com.bean;

public class StaticObjectBean {
	public static final Object A = getB();
	public static final Object B = "String B";

	public static Object getB() {
		return B;
	}
}
