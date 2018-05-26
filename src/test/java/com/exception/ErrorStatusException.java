package com.exception;

import java.text.MessageFormat;

public class ErrorStatusException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private boolean isSuccess = false;
	private String key;
	private String info;

	public ErrorStatusException(String key) {
		super(key);
		this.key = key;
		this.info = key;
	}

	public ErrorStatusException(String key, String message) {
		super(MessageFormat.format("{0}[{1}]", key, message));
		this.key = key;
		this.info = message;
	}

	public ErrorStatusException(String message, String key, String info) {
		super(message);
		this.key = key;
		this.info = info;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Throwable fillInStackTrace() {
		return this;
	}

	public String toString() {
		return MessageFormat.format("{0}[{1}]", this.key, this.info);
	}
}