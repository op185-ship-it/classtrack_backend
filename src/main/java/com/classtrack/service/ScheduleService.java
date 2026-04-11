package com.classtrack.service;

import java.util.List;
import java.util.UUID;

import com.classtrack.dto.ScheduleDto;
import com.classtrack.dto.response.ScheduleResponseDto;

public interface ScheduleService {

	List<ScheduleResponseDto> createSchehdule(ScheduleDto scheduleDto);
	
	List<ScheduleResponseDto> getClassRoomScheduleForStudent(UUID userId);
}
