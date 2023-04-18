package com.cognixia.jump.dao;

public class Grades {
	private int id;
	private int studentId;
	private int classId;
	private double grade;
	
	public Grades(int id, int studentId, int classId, double grade) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.classId = classId;
		this.grade = grade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "Grades [id=" + id + ", studentId=" + studentId + ", classId=" + classId + ", grade=" + grade + "]";
	}
	
}
