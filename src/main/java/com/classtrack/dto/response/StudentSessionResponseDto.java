package com.classtrack.dto.response;

import java.time.LocalDate;

import com.classtrack.entity.AttendanceStatus;

public class StudentSessionResponseDto {

	private String subjectName;
	private AttendanceStatus attendanceStatus;
	private LocalDate dateOfSession;
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public AttendanceStatus getAttendanceStatus() {
		return attendanceStatus;
	}
	public void setAttendanceStatus(AttendanceStatus attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}
	public LocalDate getDateOfSession() {
		return dateOfSession;
	}
	public void setDateOfSession(LocalDate dateOfSession) {
		this.dateOfSession = dateOfSession;
	}
	
	
}
