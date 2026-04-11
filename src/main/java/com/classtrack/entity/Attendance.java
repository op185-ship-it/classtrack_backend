package com.classtrack.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "attendances", schema = "test")
public class Attendance {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne()
	@JoinColumn(name = "lecture_session_id")
	private LectureSession lectureSession;
	
	@ManyToOne()
	@JoinColumn(name = "student_user_id")
	private Student student;
	
	@Column(name = "attendance_status")
	private AttendanceStatus attendaceStatus;
	
	@Column(name = "makred_at")
	private LocalDateTime makredAt;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public LectureSession getLectureSession() {
		return lectureSession;
	}

	public void setLectureSession(LectureSession lectureSession) {
		this.lectureSession = lectureSession;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public AttendanceStatus getAttendaceStatus() {
		return attendaceStatus;
	}

	public void setAttendaceStatus(AttendanceStatus attendaceStatus) {
		this.attendaceStatus = attendaceStatus;
	}

	public LocalDateTime getMakredAt() {
		return makredAt;
	}

	public void setMakredAt(LocalDateTime makredAt) {
		this.makredAt = makredAt;
	}
	
	
	
}
