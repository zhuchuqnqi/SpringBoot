package com.example.model;

import java.io.Serializable;

public class UserInfo extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private int age;

	public UserInfo() {
		super();
	}

	public UserInfo(int id ,String name, int age) {
		setId(id);
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
