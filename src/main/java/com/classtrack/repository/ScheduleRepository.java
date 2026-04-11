package com.classtrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.classtrack.entity.Schedule;
import com.classtrack.entity.Teacher;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;




public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	List<Schedule> findByDayOfWeekAndStartTimeAndEndTime(DayOfWeek dayofWeek, LocalTime startTime, LocalTime endTime);
	
	List<Schedule> findByTeacherAndDayOfWeekAndStartTimeAndEndTime(Teacher teacher, DayOfWeek dayofWeek, LocalTime startTime, LocalTime endTime);
	
	@Query("""
		    SELECT s FROM Schedule s 
		    WHERE s.classRoom.id = :classRoomId
		    AND s.dayOfWeek = :dayOfWeek
		    AND s.startTime < :endTime 
		    AND s.endTime > :startTime
		""")
		List<Schedule> findClashingSchedulesByClassRoom(
		    @Param("classRoomId") Long classRoomId,
		    @Param("dayOfWeek") DayOfWeek dayOfWeek,
		    @Param("startTime") LocalTime startTime,
		    @Param("endTime") LocalTime endTime
		);
	
	@Query("""
			SELECT s from Schedule s
			WHERE s.teacher.id = :teacherId
			AND dayOfWeek = :dayOfWeek
			AND s.startTime < :endTime 
			AND s.endTime > :startTime
			""")
	List<Schedule> findClashingScedulesList(
			@Param(value = "teacherId") UUID teacherId,
			@Param(value = "dayOfWeek") DayOfWeek dayOfWeek,
			@Param(value = "startTime") LocalTime startTime,
			@Param(value = "endTime") LocalTime endTime
			);
	
	
	List<Schedule> findByDayOfWeek(DayOfWeek dayOfWeek);
	
	
}
