package com.bean;

public abstract class AnimalBean {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AnimalBean(String name) {
		super();
		this.name = name;
	}

	public AnimalBean() {
		super();
	}

}
