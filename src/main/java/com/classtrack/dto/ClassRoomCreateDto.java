package com.classtrack.dto;

public class ClassRoomCreateDto {

	private String departmentCode;
	private Integer semester;
	private String classRoomName;
	public ClassRoomCreateDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ClassRoomCreateDto(String departmentCode, Integer semester, String classRoomName) {
		super();
		this.departmentCode = departmentCode;
		this.semester = semester;
		this.classRoomName = classRoomName;
	}
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	public Integer getSemester() {
		return semester;
	}
	public void setSemester(Integer semester) {
		this.semester = semester;
	}
	public String getClassRoomName() {
		return classRoomName;
	}
	public void setClassRoomName(String classRoomName) {
		this.classRoomName = classRoomName;
	}
	@Override
	public String toString() {
		return "ClassRoomCreateDto [departmentCode=" + departmentCode + ", semester=" + semester + ", classRoomName="
				+ classRoomName + "]";
	}
	
	
}
