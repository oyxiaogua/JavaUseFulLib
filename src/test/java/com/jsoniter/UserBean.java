package com.jsoniter;

public class UserBean {
	private String firstName;
	private String lastName;
	private int score;

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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public UserBean() {
		super();
	}

	public UserBean(String firstName, String lastName, int score) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.score = score;
	}

	public String toString() {
		return "UserBean [firstName=" + firstName + ", lastName=" + lastName + ", score=" + score + "]";
	}

}