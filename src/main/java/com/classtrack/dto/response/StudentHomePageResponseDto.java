package com.classtrack.dto.response;

import java.util.ArrayList;
import java.util.List;

public class StudentHomePageResponseDto {

	private Integer rollNumber;
	private String classRoomName;
	private Long classesHeld;
	private Long ClassesAttended;
	
	private List<ScheduleResponseDto> todaysClasses = new ArrayList<>();
	
	private List<StudentSessionResponseDto> recentSessions = new ArrayList<>();

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

	public Long getClassesHeld() {
		return classesHeld;
	}

	public void setClassesHeld(Long classesHeld) {
		this.classesHeld = classesHeld;
	}

	public Long getClassesAttended() {
		return ClassesAttended;
	}

	public void setClassesAttended(Long classesAttended) {
		ClassesAttended = classesAttended;
	}

	public List<ScheduleResponseDto> getTodaysClasses() {
		return todaysClasses;
	}

	public void setTodaysClasses(List<ScheduleResponseDto> todaysClasses) {
		this.todaysClasses = todaysClasses;
	}

	public List<StudentSessionResponseDto> getRecentSessions() {
		return recentSessions;
	}

	public void setRecentSessions(List<StudentSessionResponseDto> recentSessions) {
		this.recentSessions = recentSessions;
	}
	
	//helper method
	public void addTodaysClasses(ScheduleResponseDto dto) {
		todaysClasses.add(dto);
	}
	public void addRecentSessions(StudentSessionResponseDto dto) {
		recentSessions.add(dto);
	}	
	
}
