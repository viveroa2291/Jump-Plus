package com.cognixia.jump.dao;

public class Student {
	private int id;
	private String firstName;
	private String lastName;
	private int classId;
	
	public Student(int id, String firstName, String lastName, int classId) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.classId = classId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", classId=" + classId
				+ "]";
	}
	
}
