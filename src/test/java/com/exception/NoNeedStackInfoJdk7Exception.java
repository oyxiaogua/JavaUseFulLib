package com.exception;

public class NoNeedStackInfoJdk7Exception extends Exception{
	private static final long serialVersionUID = 1L;

	public NoNeedStackInfoJdk7Exception() {
		//最后一个参数
		super(null, null, false, false);
	}
}
