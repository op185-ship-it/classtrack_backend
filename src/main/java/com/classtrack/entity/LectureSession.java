package com.classtrack.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@Table(name = "lecture_sessions", schema = "test")
public class LectureSession {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID Id;
	
	@ManyToOne()
	@JoinColumn(name = "schedule_id")
	private Schedule schedule;
	
	@Column(name = "date_of_session")
	private LocalDate dateOfSession;
	
	@Column(name = "session_status")
	private SessionStatus sessionStatus;
	
	@ManyToOne()
	@JoinColumn(name = "subject_id")
	private Subject subject;
	
	@ManyToOne()
	@JoinColumn(name = "class_room_id")
	private ClassRoom classRoom;
	
	@OneToMany(mappedBy = "lectureSession",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Attendance> attendances = new ArrayList<>();

	public UUID getId() {
		return Id;
	}

	public void setId(UUID id) {
		Id = id;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public LocalDate getDateOfSession() {
		return dateOfSession;
	}

	public void setDateOfSession(LocalDate dateOfSession) {
		this.dateOfSession = dateOfSession;
	}

	public SessionStatus getSessionStatus() {
		return sessionStatus;
	}

	public void setSessionStatus(SessionStatus sessionStatus) {
		this.sessionStatus = sessionStatus;
	}

	public List<Attendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
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
	
	

}
