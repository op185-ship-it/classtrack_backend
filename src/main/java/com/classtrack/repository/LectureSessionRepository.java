package com.classtrack.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.classtrack.entity.AttendanceStatus;
import com.classtrack.entity.ClassRoom;
import com.classtrack.entity.LectureSession;
import com.classtrack.entity.SessionStatus;

@Repository
public interface LectureSessionRepository extends JpaRepository<LectureSession, UUID> {

	@Query("""
			SELECT ls from LectureSession ls
			WHERE ls.id =:id
			AND ls.sessionStatus = :status
			"""
			)
	Optional<LectureSession> findBySessionStatusAndById(
			@Param(value = "id") UUID id,@Param(value = "status") SessionStatus status			
			);
	
	Long countLectureSessionByClassRoom(ClassRoom classRoom);
	
	
	
}
