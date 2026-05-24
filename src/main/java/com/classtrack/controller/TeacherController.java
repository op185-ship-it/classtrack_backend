package com.classtrack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.classtrack.dto.request.TeacherRequestDto;
import com.classtrack.dto.response.TeacherResponseDto;
import com.classtrack.dto.response.TeacherAttendanceResponseDto;
import com.classtrack.dto.response.TeacherReportDto;
import com.classtrack.dto.response.TeacherReportStudentDto;
import com.classtrack.dto.response.TeacherSessionDto;
import com.classtrack.service.TeacherService;

import java.util.UUID;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class TeacherController {

	@Autowired
	TeacherService teacherService;
	
	@PostMapping("/teachers")
	public ResponseEntity<TeacherResponseDto> addTeacher(@RequestBody TeacherRequestDto dto){
		TeacherResponseDto responseDto =teacherService.createTeacher(dto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
	}
    @GetMapping("/teachers")
    public ResponseEntity<?> getAllTeachers(){
//        return teacherService.getAllTeachers();
        return  ResponseEntity.status(HttpStatus.OK).body(teacherService.getAllTeachers());
    }

    @GetMapping("/teachers/{userId}/schedules")
    public ResponseEntity<?>  getAllSchedules(@PathVariable UUID userId){
        return ResponseEntity.status(HttpStatus.OK).body(teacherService.getAllSchedules(userId));
    }

    @GetMapping("/teachers/{teacherId}/sessions")
    public ResponseEntity<List<TeacherSessionDto>> getPastSessions(@PathVariable UUID teacherId) {
        return ResponseEntity.status(HttpStatus.OK).body(teacherService.getPastSessions(teacherId));
    }

    @GetMapping("/teachers/sessions/{sessionId}")
    public ResponseEntity<TeacherAttendanceResponseDto> getSessionDetails(@PathVariable UUID sessionId) {
        return ResponseEntity.status(HttpStatus.OK).body(teacherService.getSessionDetails(sessionId));
    }

    @GetMapping("/teachers/{teacherId}/reports")
    public ResponseEntity<List<TeacherReportDto>> getClassWiseReports(@PathVariable UUID teacherId) {
        return ResponseEntity.status(HttpStatus.OK).body(teacherService.getClassWiseReports(teacherId));
    }

    @GetMapping("/teachers/reports/{scheduleId}")
    public ResponseEntity<List<TeacherReportStudentDto>> getReportDetails(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(teacherService.getReportDetails(scheduleId));
    }
}
