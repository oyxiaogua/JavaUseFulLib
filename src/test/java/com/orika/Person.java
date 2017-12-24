package com.orika;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.qala.datagen.RandomDate;
import io.qala.datagen.RandomShortApi;
import io.qala.datagen.RandomValue;

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

	public static Person random() {
		Person person = new Person();
		person.setAge(RandomShortApi.integer(1,100));
		person.setAddress(RandomShortApi.alphanumeric(5,20));
		person.setMoney(RandomShortApi.Double(10, 3000));
		person.setBirthDate(Date.from(RandomDate.between(LocalDate.now(), LocalDate.now().plusMonths(12)).localDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		person.setNameStrList(RandomValue.between(10, 20).alphanumerics(5));
		person.setName(Name.random());
		List<Name> nameList=new ArrayList<Name>();
		nameList.add(Name.random());
		nameList.add(Name.random());
		nameList.add(Name.random());
		nameList.add(Name.random());
		person.setNameList(nameList);
		return person;
	}

	public String toString() {
		return "Person [age=" + age + ", address=" + address + ", money=" + money + ", name=" + name + ", nameList="
				+ nameList + ", nameStrList=" + nameStrList + ", birthDate=" + birthDate + "]";
	}

	
}
