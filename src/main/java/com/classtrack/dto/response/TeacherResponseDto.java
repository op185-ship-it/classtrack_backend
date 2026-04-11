package com.classtrack.dto.response;

public class TeacherResponseDto {
	private String name;
	private String email;
	private String departmentName;
	
	public TeacherResponseDto(String name, String email, String departmentName) {
		super();
		this.name = name;
		this.email = email;
		this.departmentName = departmentName;
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
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}
