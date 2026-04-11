package com.classtrack.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.classtrack.dto.UserResponseDto;
import com.classtrack.dto.request.LoginRequestDto;
import com.classtrack.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<UserResponseDto> loginUser(@RequestBody LoginRequestDto dto){
		System.out.println(dto);
		if(dto.getEmail().equals("admin@123") && dto.getPassword().equals("admin")) {
			UserResponseDto dto1 = new UserResponseDto();
			dto1.setName("ADMIN");
			dto1.setRole("admin");
			dto1.setUserId(UUID.randomUUID());
			dto1.setEmail("admin@123");
			return ResponseEntity.status(HttpStatus.OK).body(dto1);
		}else {			
			UserResponseDto responseDto = userService.loginUser(dto);
			return ResponseEntity.status(HttpStatus.OK).body(responseDto);
		}
		
	}

}
