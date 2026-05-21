package com.classtrack.dto;

import java.util.ArrayList;
import java.util.List;

import com.classtrack.entity.User;

public class ClassRoomResponseDto {
	private String classRoomName;
	private String departmentName;
    private Integer sem;
    private Long noOfStudents;
	private List<UserResponseDto> students = new ArrayList<>();
	public String getClassRoomName() {
		return classRoomName;
	}
	public void setClassRoomName(String classRoomName) {
		this.classRoomName = classRoomName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public List<UserResponseDto> getStudents() {
		return students;
	}
	public void setStudents(List<UserResponseDto> students) {
		this.students = students;
	}

    public Integer getSem() {
        return sem;
    }

    public void setSem(Integer sem) {
        this.sem = sem;
    }

    public Long getNoOfStudents() {
        return noOfStudents;
    }

    public void setNoOfStudents(Long noOfStudents) {
        this.noOfStudents = noOfStudents;
    }

    //helper
	public void addStudent(UserResponseDto dto) {
		students.add(dto);
	}
	
}
