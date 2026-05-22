package com.classtrack.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.classtrack.dto.SubjectDto;
import com.classtrack.service.SubjectService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class SubjectController {

	@Autowired
	SubjectService subjectService;
	
	@PostMapping("/departments/{dept}/{sem}/subjects")
	public ResponseEntity<Map<String, String>> addSubjectToDepartment(
			@PathVariable String dept, @PathVariable Integer sem,@RequestBody SubjectDto dto){
		Map<String, String> hm = subjectService.createSubject(dept, sem, dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(hm);
		
	}
    @GetMapping("/subjects")
    public ResponseEntity<?> getAllSubjects(){
        return ResponseEntity.status(HttpStatus.OK).body(subjectService.getAllSubjects());
    }
	
}
