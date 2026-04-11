package com.classtrack.dto;

import com.classtrack.entity.UserRole;

public class UserRegDto {

	private String name;
	private String email;
	private String password;
	private UserRole role;
	private String deptCode;
	
	
	public UserRegDto(String name, String email, String password, UserRole role, String deptCode) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.deptCode = deptCode;
	}
	public UserRegDto() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	
}
