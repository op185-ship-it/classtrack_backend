package com.classtrack.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "deaprtments", schema = "test")
public class Department {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(name = "dept_name", nullable = false, unique = true)
	private String departmentName;
	
	@Column(name = "dept_code", nullable = false, unique = true)
	private String departmentCode;
	
	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<User> users = new ArrayList<>();

	@OneToMany(mappedBy = "department",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<ClassRoom> classRooms = new ArrayList<>();
	
	@OneToMany(mappedBy = "department",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Subject> subjects = new ArrayList<>();

	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<ClassRoom> getClassRooms() {
		return classRooms;
	}

	public void setClassRooms(List<ClassRoom> classRooms) {
		this.classRooms = classRooms;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	//helper methods
	public void addUser(User user) {
		user.setDepartment(this);
		users.add(user);
	}
	
	public void addSubject(Subject subject) {
		subject.setDepartment(this);
		subjects.add(subject);
		
	}
	
	public void addClassRoom(ClassRoom classroom) {
		classroom.setDepartment(this);
		classRooms.add(classroom);
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", departmentName=" + departmentName + ", departmentCode=" + departmentCode
				+ ", users=" + users + "]";
	}
	
	
}
