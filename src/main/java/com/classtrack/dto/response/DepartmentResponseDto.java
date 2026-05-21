package com.classtrack.dto.response;

public class DepartmentResponseDto {

    String departmentName;
    String departmentCode;
    long noOfClasses;
    long noOfStudents;
    long noOfTeachers;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public long getNoOfClasses() {
        return noOfClasses;
    }

    public void setNoOfClasses(long noOfClasses) {
        this.noOfClasses = noOfClasses;
    }

    public long getNoOfStudents() {
        return noOfStudents;
    }

    public void setNoOfStudents(long noOfStudents) {
        this.noOfStudents = noOfStudents;
    }

    public long getNoOfTeachers() {
        return noOfTeachers;
    }

    public void setNoOfTeachers(long noOfTeachers) {
        this.noOfTeachers = noOfTeachers;
    }
}
