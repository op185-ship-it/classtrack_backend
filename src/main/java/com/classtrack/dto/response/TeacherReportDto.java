package com.classtrack.dto.response;

public class TeacherReportDto {
    private Long scheduleId;
    private String classRoomName;
    private String subjectName;
    private String subjectCode;
    private long classesDone;

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

    public long getClassesDone() {
        return classesDone;
    }

    public void setClassesDone(long classesDone) {
        this.classesDone = classesDone;
    }
}

