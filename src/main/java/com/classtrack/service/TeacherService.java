package com.classtrack.service;

import com.classtrack.dto.request.TeacherRequestDto;
import com.classtrack.dto.response.TeacherResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TeacherService {
    TeacherResponseDto createTeacher(TeacherRequestDto dto);

    List<?> getAllTeachers();
}