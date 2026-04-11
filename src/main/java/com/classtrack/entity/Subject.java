package com.classtrack.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "subjects", schema = "test")
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "subject_name", unique = true, nullable = false)
	private String subjectName;
	
	@Column(name = "subject_code",unique = true, nullable = false)
	private String subjectCode;
	
	@Column(name = "semester", nullable = false)	
	private Integer semester;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id", nullable = false)
	private Department department;
	
	@OneToMany(mappedBy = "subject",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Schedule> schedules = new ArrayList<>();
	
	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<StudentSubjectAttendanceSummary> subjectAttendanceSummary = new ArrayList<>();

	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<LectureSession> sessions = new ArrayList<>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}

	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}
	

	public List<StudentSubjectAttendanceSummary> getSubjectAttendanceSummary() {
		return subjectAttendanceSummary;
	}

	public void setSubjectAttendanceSummary(List<StudentSubjectAttendanceSummary> subjectAttendanceSummary) {
		this.subjectAttendanceSummary = subjectAttendanceSummary;
	}

	//helper method
	public void addSchedule(Schedule schedule) {
		schedule.setSubject(this);
		schedules.add(schedule);
	}
	
	public void addAttendnaceSummary(StudentSubjectAttendanceSummary summary) {
		summary.setSubject(this);
		subjectAttendanceSummary.add(summary);
	}
	
	public void addSession(LectureSession lectureSession) {
		lectureSession.setSubject(this);
		sessions.add(lectureSession);
	}
	
	@Override
	public String toString() {
		return "Subject [id=" + id + ", subjectName=" + subjectName + ", subjectCode=" + subjectCode + ", department="
				+ department + "]";
	}
	
	
	
}
