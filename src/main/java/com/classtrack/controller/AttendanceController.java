package com.classtrack.controller;

import com.classtrack.dto.request.FlaskAttendanceMarkRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.classtrack.dto.request.AttendanceRequestDto;
import com.classtrack.dto.response.ClassStartedResponseDto;
import com.classtrack.dto.response.TeacherAttendanceResponseDto;
import com.classtrack.service.AttendanceService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

	@Autowired
	AttendanceService attendanceService;
	
	@PostMapping("/sessions/{scheduleId}")
	public ResponseEntity<ClassStartedResponseDto> startClass(@PathVariable Long scheduleId){
		ClassStartedResponseDto dto = attendanceService.startClass(scheduleId);
		
		return ResponseEntity.status(HttpStatus.OK).body(dto);
		
	}
	
	@PostMapping("/teachers/attendance")
	public ResponseEntity<TeacherAttendanceResponseDto> markAttendance(@RequestBody AttendanceRequestDto requestDto){
		
		TeacherAttendanceResponseDto dto = attendanceService.markAttendance(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
		
	}

	/**
	 * Called by Flask after face recognition.
	 * Expects sessionId + presentRollNumbers; Spring will mark everyone else ABSENT.
	 */
	@PostMapping("/flask/attendance")
	public ResponseEntity<TeacherAttendanceResponseDto> markAttendanceFromFlask(
			@RequestBody FlaskAttendanceMarkRequestDto requestDto) {

		TeacherAttendanceResponseDto dto = attendanceService.markAttendanceFromFlask(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}

	
}
