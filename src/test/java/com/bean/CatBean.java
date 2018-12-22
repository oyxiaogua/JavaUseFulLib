package com.bean;

public class CatBean extends AnimalBean {

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toString() {
		return "CatBean [type=" + type + ", getName()=" + getName() + "]";
	}

	public CatBean(String name, String type) {
		super(name);
		this.type = type;
	}

	public CatBean(String name) {
		super(name);
	}

	public CatBean() {
	}

}
