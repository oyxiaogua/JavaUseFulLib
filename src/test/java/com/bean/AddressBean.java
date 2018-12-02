package com.bean;

public class AddressBean {

	private String province;
	private String city;
	private String area;

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public AddressBean(String province, String city, String area) {
		super();
		this.province = province;
		this.city = city;
		this.area = area;
	}

	public AddressBean() {
		super();
	}

	public String toString() {
		return "AddressBean [province=" + province + ", city=" + city + ", area=" + area + "]";
	}
	
	

}
