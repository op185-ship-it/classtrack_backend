package com.classtrack.dto.response;

import com.classtrack.entity.AttendanceStatus;

public class AttendanceEntryDto {

	private String studentName;
	private Integer rollNumber;
	private AttendanceStatus status;
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Integer getRollNumber() {
		return rollNumber;
	}
	public void setRollNumber(Integer rollNumber) {
		this.rollNumber = rollNumber;
	}
	public AttendanceStatus getStatus() {
		return status;
	}
	public void setStatus(AttendanceStatus status) {
		this.status = status;
	}
	
}
