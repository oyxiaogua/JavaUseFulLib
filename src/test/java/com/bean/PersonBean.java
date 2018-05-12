package com.bean;

import java.util.List;

public class PersonBean {
	private int id;
	private String name;
	private List<String> addressList;
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
	public List<String> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<String> addressList) {
		this.addressList = addressList;
	}
	public PersonBean(int id, String name, List<String> addressList) {
		super();
		this.id = id;
		this.name = name;
		this.addressList = addressList;
	}
	public PersonBean() {
		super();
	}
	public String toString() {
		return "PersonBean [id=" + id + ", name=" + name + ", addressList=" + addressList + "]";
	}
	
}
