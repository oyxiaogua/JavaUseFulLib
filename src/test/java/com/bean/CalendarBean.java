package com.bean;

import java.util.Calendar;
import java.util.Date;

public class CalendarBean {
	private String name;
	private Date createDate;
	private Calendar calendar;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Calendar getCalendar() {
		return calendar;
	}
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}
	public String toString() {
		return "CalendarBean [name=" + name + ", createDate=" + createDate + ", calendar=" + calendar + "]";
	}
}
