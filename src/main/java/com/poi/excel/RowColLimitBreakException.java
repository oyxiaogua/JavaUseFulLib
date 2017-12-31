package com.poi.excel;
/**
 * 表示 excel 超出最大行数、列数的异常类
 */
public class RowColLimitBreakException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RowColLimitBreakException(String msg) {
		super(msg);
	}
}