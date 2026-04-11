package com.classtrack.service;

import java.util.List;
import java.util.UUID;

import com.classtrack.dto.request.StudentRequestDto;
import com.classtrack.dto.response.AttendanceSummaryPerSubjectDto;
import com.classtrack.dto.response.StudentHomePageResponseDto;
import com.classtrack.dto.response.StudentResponseDto;
import com.classtrack.dto.response.StudentSessionResponseDto;

public interface StudentService {
	StudentResponseDto createStudent(StudentRequestDto dto);
	
	StudentHomePageResponseDto homePageResponse(UUID userId);
	
	List<StudentSessionResponseDto> getAllSessionsByStudentId(UUID uuid);
	
	List<AttendanceSummaryPerSubjectDto> getSubjectWiseAttendanceByStudentId(UUID userId);
}
