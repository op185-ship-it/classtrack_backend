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
@Table(name = "class_rooms", schema = "test")
public class ClassRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "class_room_name",unique = true)
	private String classRoomName;
	
	@Column(name = "semester", nullable = false)
	private Integer semester;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id",nullable = false)
	private Department department;
	
	@OneToMany(mappedBy = "classRoom",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Student> students = new ArrayList<>();

	@OneToMany(mappedBy = "classRoom",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Schedule> schedules = new ArrayList<>();
	
	@OneToMany(mappedBy = "classRoom", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<StudentSubjectAttendanceSummary> subjectAttendanceSummary = new ArrayList<>();
	
	@OneToMany(mappedBy = "classRoom", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<LectureSession> lectureSessions = new ArrayList<>();
	
	
	
	public ClassRoom() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClassRoomName() {
		return classRoomName;
	}

	public void setClassRoomName(String classRoomName) {
		this.classRoomName = classRoomName;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	//helper methods
	public void addStudents(Student student) {
		student.setClassRoom(this);
		students.add(student);
		
	}
	public void addSchedule(Schedule schedule) {
		schedule.setClassRoom(this);
		schedules.add(schedule);
	}
	public void addAttendnaceSummary(StudentSubjectAttendanceSummary summary) {
		summary.setClassRoom(this);
		subjectAttendanceSummary.add(summary);
	}
}
