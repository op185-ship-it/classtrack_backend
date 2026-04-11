package com.classtrack.dto.request;

import java.util.Map;
import java.util.UUID;

import com.classtrack.entity.AttendanceStatus;

public class AttendanceRequestDto {
	private UUID sessionId;
	
	private Map<Integer, AttendanceStatus> attendanceReport;

	public UUID getSessionId() {
		return sessionId;
	}

	public void setSessionId(UUID sessionId) {
		this.sessionId = sessionId;
	}

	public Map<Integer, AttendanceStatus> getAttendanceReport() {
		return attendanceReport;
	}

	public void setAttendanceReport(Map<Integer, AttendanceStatus> attendanceReport) {
		this.attendanceReport = attendanceReport;
	}
	
}
