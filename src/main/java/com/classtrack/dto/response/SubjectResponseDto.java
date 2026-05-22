package com.classtrack.dto.response;

public class SubjectResponseDto {
    private String subjectNameAndCode;
    private Integer semester;
    private String departmentNameAndCode;

    public String getSubjectNameAndCode() {
        return subjectNameAndCode;
    }

    public void setSubjectNameAndCode(String subjectNameAndCode) {
        this.subjectNameAndCode = subjectNameAndCode;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getDepartmentNameAndCode() {
        return departmentNameAndCode;
    }

    public void setDepartmentNameAndCode(String departmentNameAndCode) {
        this.departmentNameAndCode = departmentNameAndCode;
    }
}
