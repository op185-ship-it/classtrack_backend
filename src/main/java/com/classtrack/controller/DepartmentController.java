package com.classtrack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.classtrack.entity.Department;
import com.classtrack.repository.DepartmentRepository;
import com.classtrack.repository.ScheduleRepository;
import com.classtrack.service.DepartmentService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class DepartmentController {
	
	@Autowired
	DepartmentService departmentService;
	@Autowired
	DepartmentRepository  departmentRepository;
	@Autowired
	ScheduleRepository scheduleRepository;
	
	
	//for creating department with name and code of department 
	@PostMapping("/departments")
	public ResponseEntity<Department> createDepartment(@RequestBody Department department){
		
		Department d = departmentService.createDepartment(department);
		return ResponseEntity.status(HttpStatus.CREATED).body(d);
		
	}
	
	@GetMapping("/departments")
	public ResponseEntity<List<Department>> getAllDepartments(){
		
		return ResponseEntity.status(HttpStatus.FOUND).body(departmentService.getAllDepartments());
		
	}
	
	@GetMapping("/test")
	public ResponseEntity<String>test(){
		
		
//		System.out.println(scheduleRepository.findByDayofWeekAndStartTimeAndEndTime(DayOfWeek.TUESDAY, "10:45", null).orElseThrow(()-> new RuntimeException("error")));
		return ResponseEntity.status(HttpStatus.FOUND).body("ok");
	}
}
