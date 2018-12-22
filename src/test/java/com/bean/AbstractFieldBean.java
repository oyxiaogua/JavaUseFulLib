package com.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class AbstractFieldBean {
	@JsonDeserialize(as = CatBean.class)
	private AnimalBean bean = new CatBean("cat", "animal");
	private int id;

	public AnimalBean getBean() {
		return bean;
	}

	public void setBean(AnimalBean bean) {
		this.bean = bean;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AbstractFieldBean() {
		super();
	}

	public AbstractFieldBean(int id) {
		super();
		this.id = id;
	}

	public AbstractFieldBean(AnimalBean bean, int id) {
		super();
		this.bean = bean;
		this.id = id;
	}

	public String toString() {
		return "AbstractFieldBean [bean=" + bean + ", id=" + id + "]";
	}

}
