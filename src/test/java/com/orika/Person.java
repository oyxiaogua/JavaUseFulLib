package com.orika;

import java.util.Date;
import java.util.List;

public class Person {
	private int age;
	private String address;
	private double money;
	private Name name;
	private List<Name> nameList;
	private List<String> nameStrList;
	private Date birthDate;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public List<Name> getNameList() {
		return nameList;
	}

	public void setNameList(List<Name> nameList) {
		this.nameList = nameList;
	}

	public List<String> getNameStrList() {
		return nameStrList;
	}

	public void setNameStrList(List<String> nameStrList) {
		this.nameStrList = nameStrList;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

}
