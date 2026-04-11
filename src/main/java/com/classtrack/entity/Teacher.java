package com.classtrack.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "teachers",schema = "test")
@DiscriminatorValue("TEACHER")
public class Teacher extends User{

	
	@Column(name = "employee_id", unique = true)
	private String employeeId;

	@OneToMany(mappedBy = "teacher",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Schedule> schedules = new ArrayList<>();
	
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}
	
	//helper methods
	public void addSchedule(Schedule schedule) {
		schedule.setTeacher(this);
		schedules.add(schedule);
	}
	
	
	
}
