package com.classtrack.dto.response;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.classtrack.entity.AttendanceStatus;

public class TeacherAttendanceResponseDto {

	private UUID sessionId;
	
	private String clasRoomName;
	
	private String subjectNameAndCode;
	
	private LocalDate dateOfLecture;
	
	List<AttendanceEntryDto> attendanceResult;


	public UUID getSessionId() {
		return sessionId;
	}

	public void setSessionId(UUID sessionId) {
		this.sessionId = sessionId;
	}

	public String getClasRoomName() {
		return clasRoomName;
	}

	public void setClasRoomName(String clasRoomName) {
		this.clasRoomName = clasRoomName;
	}

	public String getSubjectNameAndCode() {
		return subjectNameAndCode;
	}

	public void setSubjectNameAndCode(String subjectNameAndCode) {
		this.subjectNameAndCode = subjectNameAndCode;
	}

	public LocalDate getDateOfLecture() {
		return dateOfLecture;
	}

	public void setDateOfLecture(LocalDate dateOfLecture) {
		this.dateOfLecture = dateOfLecture;
	}

	public List<AttendanceEntryDto> getAttendanceResult() {
		return attendanceResult;
	}

	public void setAttendanceResult(List<AttendanceEntryDto> attendanceResult) {
		this.attendanceResult = attendanceResult;
	}
	
	
}
