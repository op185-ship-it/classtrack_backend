package com.classtrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.classtrack.dto.UserResponseDto;
import com.classtrack.dto.request.LoginRequestDto;
import com.classtrack.entity.Student;
import com.classtrack.entity.Teacher;
import com.classtrack.entity.User;
import com.classtrack.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repository;
	
	public UserResponseDto loginUser(LoginRequestDto dto) {
		User user = repository
				.findByEmailAndPassword(dto.getEmail(), dto.getPassword())
				.orElseThrow(()->new RuntimeException("no user found"));
		
		UserResponseDto dto2 = new UserResponseDto();
		if(user instanceof Teacher) {
			dto2.setRole("teacher");
		}else if(user instanceof Student) {
			dto2.setRole("student");
		}
		dto2.setName(user.getName());
		dto2.setEmail(user.getEmail());
		dto2.setUserId(user.getId());
		return dto2;
		}
}
