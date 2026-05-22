package com.classtrack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.classtrack.dto.request.TeacherRequestDto;
import com.classtrack.dto.response.TeacherResponseDto;
import com.classtrack.service.TeacherService;

import java.util.UUID;

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
}
