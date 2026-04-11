package com.classtrack.dto.response;

import java.util.UUID;

public class ClassStartedResponseDto {

	private UUID sessionId;
	
	private String message;

	public UUID getSessionId() {
		return sessionId;
	}

	public void setSessionId(UUID sessionId) {
		this.sessionId = sessionId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
