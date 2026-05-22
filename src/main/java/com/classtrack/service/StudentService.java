package com.classtrack.service;

import java.util.List;
import java.util.UUID;

import com.classtrack.dto.request.StudentRequestDto;
import com.classtrack.dto.response.*;
import org.jspecify.annotations.Nullable;

public interface StudentService {
	StudentResponseDto createStudent(StudentRequestDto dto);
	
	StudentHomePageResponseDto homePageResponse(UUID userId);
	
	List<StudentSessionResponseDto> getAllSessionsByStudentId(UUID uuid);
	
	List<AttendanceSummaryPerSubjectDto> getSubjectWiseAttendanceByStudentId(UUID userId);

    BasicStudentDto getInfoForImageStore(UUID userID);

    @Nullable List<StudentResponseDto> getAllStudents();
}
