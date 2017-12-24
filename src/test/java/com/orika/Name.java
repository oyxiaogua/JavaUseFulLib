package com.orika;

import io.qala.datagen.RandomShortApi;

public class Name {
	private String first;
	private String last;

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}
	
	public static Name random(){
		Name name = new Name();
		name.setFirst(RandomShortApi.alphanumeric(5,20));
		name.setLast(RandomShortApi.alphanumeric(5,20));
		return name;
	}

	public String toString() {
		return "Name [first=" + first + ", last=" + last + "]";
	}

}
