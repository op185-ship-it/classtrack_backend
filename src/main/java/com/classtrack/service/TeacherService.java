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

    List<com.classtrack.dto.response.TeacherSessionDto> getPastSessions(UUID teacherId);

    com.classtrack.dto.response.TeacherAttendanceResponseDto getSessionDetails(UUID sessionId);

    List<com.classtrack.dto.response.TeacherReportDto> getClassWiseReports(UUID teacherId);

    List<com.classtrack.dto.response.TeacherReportStudentDto> getReportDetails(Long scheduleId);
}
