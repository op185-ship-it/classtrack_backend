package com.classtrack.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.classtrack.dto.ClassRoomCreateDto;
import com.classtrack.dto.ClassRoomEnrollDto;
import com.classtrack.dto.ClassRoomResponseDto;
import com.classtrack.dto.response.ScheduleResponseDto;
import com.classtrack.entity.ClassRoom;

public interface ClassRoomService {

	Map<String, String> createClassRoom(ClassRoomCreateDto dto);
	
//	ClassRoomResponseDto enrollClass(String classRoomName,ClassRoomEnrollDto dto);
	
	
}
