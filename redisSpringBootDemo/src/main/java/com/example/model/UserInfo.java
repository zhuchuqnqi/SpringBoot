package com.example.model;


public class UserInfo extends BaseEntity{

	private String name;

	private Integer age;

	public UserInfo() {
		super();
	}

	public UserInfo(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
