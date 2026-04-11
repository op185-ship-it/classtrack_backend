package com.classtrack.dto.response;

import java.util.ArrayList;
import java.util.List;

public class AttendanceSummaryWithStudentDto {

	private String studentName;
	private Integer rollNumber;
	private String classRoomName;
	private Integer semsester;
	
	private List<AttendanceSummaryPerSubjectDto> subjectWiseAttendance = new ArrayList<>();
	
	private Long overallAttendance;

	

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

	public String getClassRoomName() {
		return classRoomName;
	}

	public void setClassRoomName(String classRoomName) {
		this.classRoomName = classRoomName;
	}

	public Integer getSemsester() {
		return semsester;
	}

	public void setSemsester(Integer semsester) {
		this.semsester = semsester;
	}

	public List<AttendanceSummaryPerSubjectDto> getSubjectWiseAttendance() {
		return subjectWiseAttendance;
	}

	public void setSubjectWiseAttendance(List<AttendanceSummaryPerSubjectDto> subjectWiseAttendance) {
		this.subjectWiseAttendance = subjectWiseAttendance;
	}

	public Long getOverallAttendance() {
		return overallAttendance;
	}

	public void setOverallAttendance(Long overallAttendance) {
		this.overallAttendance = overallAttendance;
	}
	
}
