package com.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticFieldClass {
	private static final Logger log = LoggerFactory.getLogger(StaticFieldClass.class);

	static {
		log.info("执行了静态代码块");
	}

	// 静态变量
	public static String staticFiled = staticMethod();

	// 赋值静态变量的静态方法
	public static String staticMethod() {
		log.info("执行了静态方法");
		return "test";
	}

	public StaticFieldClass() {
		super();
		log.info("执行了构造方法");
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}