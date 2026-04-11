package com.classtrack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "attendance_summary", schema = "test")
public class StudentSubjectAttendanceSummary {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne()
	@JoinColumn(name = "student_user_id")
	private Student student;
	
	@ManyToOne()
	@JoinColumn(name = "subject_id")
	private Subject subject;
	
	@ManyToOne()
	@JoinColumn(name = "class_room_id")
	private ClassRoom classRoom;
	
	private Long classesHeld;
	
	private Long classesAttended;
	
	
	

	public StudentSubjectAttendanceSummary() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentSubjectAttendanceSummary(Student student, Subject subject, ClassRoom classRoom, Long classesHeld,
			Long classesAttended) {
		super();
		this.student = student;
		this.subject = subject;
		this.classRoom = classRoom;
		this.classesHeld = classesHeld;
		this.classesAttended = classesAttended;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public ClassRoom getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(ClassRoom classRoom) {
		this.classRoom = classRoom;
	}

	public Long getClassesHeld() {
		return classesHeld;
	}

	public void setClassesHeld(Long classesHeld) {
		this.classesHeld = classesHeld;
	}

	public Long getClassesAttended() {
		return classesAttended;
	}

	public void setClassesAttended(Long classesAttended) {
		this.classesAttended = classesAttended;
	}
	

}
