package com.classtrack.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.classtrack.dto.request.ScheduleRequestDto;
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

import com.classtrack.dto.ClassRoomCreateDto;
import com.classtrack.dto.ClassRoomEnrollDto;
import com.classtrack.dto.ClassRoomResponseDto;
import com.classtrack.dto.ScheduleDto;
import com.classtrack.dto.response.ScheduleResponseDto;
import com.classtrack.entity.ClassRoom;
import com.classtrack.service.ClassRoomService;
import com.classtrack.service.ScheduleService;

import jakarta.websocket.server.PathParam;
@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ClassRoomController {

	@Autowired
	ClassRoomService classRoomService;
	@Autowired
	ScheduleService scheduleService;
	
	@PostMapping("/classrooms")
	public ResponseEntity<Map<String, String>> createClassRoom(@RequestBody ClassRoomCreateDto dto){
		
		Map<String, String> cls = classRoomService.createClassRoom(dto);
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cls);
		
	}
	
//	@PostMapping("/classrooms/{classRoomName}/enrollments")
//	public ResponseEntity<ClassRoomResponseDto> enrollStudent(@PathVariable String classRoomName,@RequestBody ClassRoomEnrollDto dto){
//		ClassRoomResponseDto cls = classRoomService.enrollClass(classRoomName, dto);
//		System.out.println("ClassRoomController.enrollStudent()");
//		System.out.println(cls);
//		return ResponseEntity.status(HttpStatus.CREATED).body(cls);
//		
//	}
	
	@PostMapping("/v1/schedules")
	public ResponseEntity<List<ScheduleResponseDto>> createSchedule(@RequestBody ScheduleDto dto){
		
		List<ScheduleResponseDto> li = scheduleService.createSchehdule(dto);
		return ResponseEntity.status(HttpStatus.FOUND).body(li);
		
	}

    @PostMapping("/v2/schedules")
    public ResponseEntity<?> createScheduleNew(@RequestBody ScheduleRequestDto scheduleRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.createSchehduleNew(scheduleRequestDto));
    }

	@GetMapping("/schedules/{userId}")
	public ResponseEntity<List<ScheduleResponseDto>> getSchedules(@PathVariable UUID userId){
		
		List<ScheduleResponseDto> li = scheduleService.getClassRoomScheduleForStudent(userId);
		return ResponseEntity.status(HttpStatus.OK).body(li);
	}
    @GetMapping("/schedules")
    public ResponseEntity<?> getAllSchedules(){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAllSchedules());
    }
    @GetMapping("/classrooms/{classRoomName}/students")
    public ResponseEntity<?> findAllStudentsByClassRoomName(@PathVariable String classRoomName){
        return ResponseEntity.status(HttpStatus.OK).body(classRoomService.findAllStudentsByClassRoomName(classRoomName));
    }

	
}
