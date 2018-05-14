package com.exception;

public class NoNeedStackInfoException extends Exception{
	private static final long serialVersionUID = 1L;

	public Throwable fillInStackTrace() {
		return this;
	}
}
