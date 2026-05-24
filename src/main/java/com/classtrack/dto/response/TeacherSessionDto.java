package com.classtrack.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import com.classtrack.entity.SessionStatus;

public class TeacherSessionDto {
    private UUID sessionId;
    private Long scheduleId;
    private String classRoomName;
    private String subjectName;
    private String subjectCode;
    private LocalDate dateOfSession;
    private SessionStatus sessionStatus;
    private long presentCount;
    private long absentCount;

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getClassRoomName() {
        return classRoomName;
    }

    public void setClassRoomName(String classRoomName) {
        this.classRoomName = classRoomName;
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

    public long getPresentCount() {
        return presentCount;
    }

    public void setPresentCount(long presentCount) {
        this.presentCount = presentCount;
    }

    public long getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(long absentCount) {
        this.absentCount = absentCount;
    }
}

