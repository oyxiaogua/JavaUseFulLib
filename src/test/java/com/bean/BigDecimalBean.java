package com.bean;

import java.math.BigDecimal;

public class BigDecimalBean {
	private String name;
	private BigDecimal am1;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAm1() {
		return am1;
	}

	public void setAm1(BigDecimal am1) {
		this.am1 = am1;
	}

	public BigDecimalBean(String name, String am1) {
		super();
		this.name = name;
		this.am1 = new BigDecimal(am1);
	}

	public BigDecimalBean() {
		super();
	}

	public String toString() {
		return "BigDecimalBean [name=" + name + ", am1=" + am1.toPlainString() + "]";
	}

}
