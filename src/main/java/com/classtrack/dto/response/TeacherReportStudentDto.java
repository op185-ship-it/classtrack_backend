package com.classtrack.dto.response;

public class TeacherReportStudentDto {
    private Integer rollNumber;
    private String studentName;
    private Long classesHeld;
    private Long classesAttended;
    private Long percentage;

    public Integer getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(Integer rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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

    public Long getPercentage() {
        return percentage;
    }

    public void setPercentage(Long percentage) {
        this.percentage = percentage;
    }
}

