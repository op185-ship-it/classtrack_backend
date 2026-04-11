package com.classtrack.dto;

import java.util.UUID;

public class UserResponseDto {
	
	 private String name;
	 private String email;
	 private String role;
	 private UUID userId;
	 public String getName() {
		 return name;
	 }
	 public void setName(String name) {
		 this.name = name;
	 }
	 public String getEmail() {
		 return email;
	 }
	 public void setEmail(String email) {
		 this.email = email;
	 }
	 public String getRole() {
		 return role;
	 }
	 public void setRole(String role) {
		 this.role = role;
	 }
	 public UUID getUserId() {
		 return userId;
	 }
	 public void setUserId(UUID userId) {
		 this.userId = userId;
	 }
	 
	    
}
