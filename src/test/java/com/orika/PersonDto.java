package com.orika;

import java.util.Date;
import java.util.List;

public class PersonDto {
	private int age;
	private String address;
	private double money;
	private String firstName;
	private String lastName;
	private List<String> displayNameList;
	private Date birthDate;
	private String[][] nameArr;

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<String> getDisplayNameList() {
		return displayNameList;
	}

	public void setDisplayNameList(List<String> displayNameList) {
		this.displayNameList = displayNameList;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String[][] getNameArr() {
		return nameArr;
	}

	public void setNameArr(String[][] nameArr) {
		this.nameArr = nameArr;
	}
}