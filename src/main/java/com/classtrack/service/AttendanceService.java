package com.classtrack.service;

import java.util.List;

import com.classtrack.dto.request.AttendanceRequestDto;
import com.classtrack.dto.response.AttendanceSummaryWithStudentDto;
import com.classtrack.dto.response.ClassStartedResponseDto;
import com.classtrack.dto.response.FlaskAttendanceResponse;
import com.classtrack.dto.response.TeacherAttendanceResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface AttendanceService {

	public ClassStartedResponseDto startClass(Long scheduleId);
	
	public TeacherAttendanceResponseDto markAttendance(AttendanceRequestDto requestDto);
	
	
	
	public void markAttendanceSummary(String classRoomName);



}
