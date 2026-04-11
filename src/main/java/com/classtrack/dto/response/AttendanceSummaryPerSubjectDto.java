package com.classtrack.dto.response;

public class AttendanceSummaryPerSubjectDto {

	private String subjectname;
	private String subjectCode;
	private Long classesAttended;
	private Long classesHeld;
	private Long percentage;
	public String getSubjectname() {
		return subjectname;
	}
	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public Long getClassesAttended() {
		return classesAttended;
	}
	public void setClassesAttended(Long classesAttended) {
		this.classesAttended = classesAttended;
	}
	public Long getClassesHeld() {
		return classesHeld;
	}
	public void setClassesHeld(Long classesHeld) {
		this.classesHeld = classesHeld;
	}
	public Long getPercentage() {
		return percentage;
	}
	public void setPercentage(Long percentage) {
		this.percentage = percentage;
	}
	
	
}
