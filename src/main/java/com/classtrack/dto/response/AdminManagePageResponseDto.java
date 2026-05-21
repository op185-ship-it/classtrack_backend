package com.classtrack.dto.response;

import com.classtrack.dto.ClassRoomResponseDto;
import com.classtrack.entity.ClassRoom;
import com.classtrack.entity.Department;

import java.util.List;

public class AdminManagePageResponseDto {
    private Long noOfDepartments;
    private Long noOfClassrooms;
    private Long noOfStudents;
    private Long noOfTeachers;
    List<DepartmentResponseDto> departments;
    List<ClassRoomResponseDto> classRooms;

    public AdminManagePageResponseDto() {
    }

    public AdminManagePageResponseDto(Long noOfDepartments, Long noOfClassrooms, Long noOfStudents, Long noOfTeachers, List<DepartmentResponseDto> departments, List<ClassRoomResponseDto> classRooms) {
        this.noOfDepartments = noOfDepartments;
        this.noOfClassrooms = noOfClassrooms;
        this.noOfStudents = noOfStudents;
        this.noOfTeachers = noOfTeachers;
        this.departments = departments;
        this.classRooms = classRooms;
    }

    public Long getNoOfDepartments() {
        return noOfDepartments;
    }

    public void setNoOfDepartments(Long noOfDepartments) {
        this.noOfDepartments = noOfDepartments;
    }

    public Long getNoOfClassrooms() {
        return noOfClassrooms;
    }

    public void setNoOfClassrooms(Long noOfClassrooms) {
        this.noOfClassrooms = noOfClassrooms;
    }

    public Long getNoOfStudents() {
        return noOfStudents;
    }

    public void setNoOfStudents(Long noOfStudents) {
        this.noOfStudents = noOfStudents;
    }

    public Long getNoOfTeachers() {
        return noOfTeachers;
    }

    public void setNoOfTeachers(Long noOfTeachers) {
        this.noOfTeachers = noOfTeachers;
    }

    public List<DepartmentResponseDto> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DepartmentResponseDto> departments) {
        this.departments = departments;
    }

    public List<ClassRoomResponseDto> getClassRooms() {
        return classRooms;
    }

    public void setClassRooms(List<ClassRoomResponseDto> classRooms) {
        this.classRooms = classRooms;
    }
}
