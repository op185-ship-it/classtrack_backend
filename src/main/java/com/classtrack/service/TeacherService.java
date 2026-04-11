package com.classtrack.service;

import com.classtrack.dto.request.TeacherRequestDto;
import com.classtrack.dto.response.TeacherResponseDto;

public interface TeacherService {
    TeacherResponseDto createTeacher(TeacherRequestDto dto);
}