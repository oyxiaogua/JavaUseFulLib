package com.bean;

import java.util.Map;

public class JsonTestBean {
	private int id;
	private String name;
	private Map<Long, String> extraMap;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Long, String> getExtraMap() {
		return extraMap;
	}

	public void setExtraMap(Map<Long, String> extraMap) {
		this.extraMap = extraMap;
	}

	public JsonTestBean(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public JsonTestBean() {
		super();
	}

	public String toString() {
		return "JsonTestBean [id=" + id + ", name=" + name + ", extraMap=" + extraMap + "]";
	}

}
