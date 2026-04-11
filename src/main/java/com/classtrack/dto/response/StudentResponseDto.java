package com.classtrack.dto.response;

public class StudentResponseDto {

	private String name;
	private String email;
	private String enrolledClass;
	private String Department;
	private Integer semester;
	
	
	public StudentResponseDto(String name, String email, String enrolledClass, String department, Integer semester) {
		super();
		this.name = name;
		this.email = email;
		this.enrolledClass = enrolledClass;
		Department = department;
		this.semester = semester;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEnrolledClass() {
		return enrolledClass;
	}
	public void setEnrolledClass(String enrolledClass) {
		this.enrolledClass = enrolledClass;
	}
	public String getDepartment() {
		return Department;
	}
	public void setDepartment(String department) {
		Department = department;
	}
	public Integer getSemester() {
		return semester;
	}
	public void setSemester(Integer semester) {
		this.semester = semester;
	}
	
	
}
