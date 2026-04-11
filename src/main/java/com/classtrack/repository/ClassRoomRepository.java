package com.classtrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.classtrack.entity.ClassRoom;
import java.util.Optional;


public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {

	Optional<ClassRoom> findByClassRoomName(String classRoomName);
	
}
