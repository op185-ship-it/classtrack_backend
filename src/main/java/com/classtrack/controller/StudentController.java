package com.classtrack.controller;

import java.util.List;
import java.util.UUID;

import com.classtrack.dto.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.classtrack.dto.request.StudentRequestDto;
import com.classtrack.service.StudentService;
@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class StudentController {

	@Autowired
	StudentService studentService;
	
	@PostMapping("/students")
	public ResponseEntity<StudentResponseDto> createStudent(@RequestBody StudentRequestDto dto){
		
		StudentResponseDto responseDto = studentService.createStudent(dto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
		
	}
	
	@GetMapping("/studentHomePage/{userId}")
	public ResponseEntity<StudentHomePageResponseDto> homePageResponse(@PathVariable UUID userId){
		
		StudentHomePageResponseDto responseDto = studentService.homePageResponse(userId);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
		
	}
	
	@GetMapping("/student-session-history/{userId}")
	public ResponseEntity<List<StudentSessionResponseDto>> getAllSessionsByStudentId(@PathVariable UUID userId){
		List<StudentSessionResponseDto> li = studentService.getAllSessionsByStudentId(userId);
		
		return ResponseEntity.status(HttpStatus.OK).body(li);
	}
	
	@GetMapping("/student-subject-wise-attendace/{userId}")
	public ResponseEntity<List<AttendanceSummaryPerSubjectDto>> getSubjectWiseAttendanceByStudentId(@PathVariable UUID userId){
		List<AttendanceSummaryPerSubjectDto> li = studentService.getSubjectWiseAttendanceByStudentId(userId);
		
		return ResponseEntity.status(HttpStatus.OK).body(li);
	}

    @GetMapping("/students/info-for-image-store/{userId}")
    public ResponseEntity<BasicStudentDto> getInfo(@PathVariable UUID userId){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getInfoForImageStore(userId));
    }

    @GetMapping("/students")
    public ResponseEntity<?> getAllStudents(){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getAllStudents());
    }
}
