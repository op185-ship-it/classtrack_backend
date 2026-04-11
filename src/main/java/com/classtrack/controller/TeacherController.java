package com.classtrack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.classtrack.dto.request.TeacherRequestDto;
import com.classtrack.dto.response.TeacherResponseDto;
import com.classtrack.service.TeacherService;

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
}
