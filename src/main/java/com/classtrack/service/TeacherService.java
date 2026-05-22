package com.classtrack.service;

import com.classtrack.dto.request.TeacherRequestDto;
import com.classtrack.dto.response.ScheduleResponseDto;
import com.classtrack.dto.response.TeacherResponseDto;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface TeacherService {
    TeacherResponseDto createTeacher(TeacherRequestDto dto);

    List<?> getAllTeachers();

    @Nullable List<ScheduleResponseDto> getAllSchedules(UUID userId);
}