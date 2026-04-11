package com.classtrack.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "students",schema = "test")
@DiscriminatorValue("STUDENT")
public class Student extends User {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_room_id")
	private ClassRoom classRoom;
	
	@Column(name = "roll_number",unique = true)
	private Integer rollNumber;
	
	@OneToMany(mappedBy = "student",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Attendance> attendances = new ArrayList<>();
	
	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<StudentSubjectAttendanceSummary> subjectAttendanceSummary = new ArrayList<>(); 

	public ClassRoom getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(ClassRoom classRoom) {
		this.classRoom = classRoom;
	}

	public Integer getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(Integer rollNumber) {
		this.rollNumber = rollNumber;
	}

	public List<Attendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}
	
	public List<StudentSubjectAttendanceSummary> getSubjectAttendanceSummary() {
		return subjectAttendanceSummary;
	}

	public void setSubjectAttendanceSummary(List<StudentSubjectAttendanceSummary> subjectAttendanceSummary) {
		this.subjectAttendanceSummary = subjectAttendanceSummary;
	}

	//helper methods
	public void addAttendance(Attendance attendance) {
		attendance.setStudent(this);
		attendances.add(attendance);
	}
	public void addAttendanceSummary(StudentSubjectAttendanceSummary attendanceSummary) {
		attendanceSummary.setStudent(this);
		subjectAttendanceSummary.add(attendanceSummary);
	}
}
