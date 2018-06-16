package com.bean;

public class UserBean {
	private String name;
	private String phone;
	private AddressBean address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public AddressBean getAddress() {
		return address;
	}

	public void setAddress(AddressBean address) {
		this.address = address;
	}

	public UserBean(String name, String phone, AddressBean address) {
		super();
		this.name = name;
		this.phone = phone;
		this.address = address;
	}

	public UserBean(String name, String phone) {
		super();
		this.name = name;
		this.phone = phone;
	}

	public UserBean() {
		super();
	}

}
