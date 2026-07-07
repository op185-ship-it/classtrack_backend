package com.classtrack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.classtrack.entity.ClassRoom;
import com.classtrack.entity.Student;
import com.classtrack.entity.StudentSubjectAttendanceSummary;
import com.classtrack.entity.Subject;

@Repository
public interface StudentSubjectAttendanceSummaryRepository extends JpaRepository<StudentSubjectAttendanceSummary, Long> {

	Optional<StudentSubjectAttendanceSummary> findByStudentAndSubjectAndClassRoom(
		    Student student, Subject subject, ClassRoom classRoom
		);
	
	@Query("select coalesce(sum(s.classesHeld), 0) from StudentSubjectAttendanceSummary s where s.student = :student")
	Long sumClassesHeldByStudent(@Param("student") Student student);

	@Query("select coalesce(sum(s.classesAttended), 0) from StudentSubjectAttendanceSummary s where s.student = :student")
	Long sumClassesAttendedByStudent(@Param("student") Student student);

	java.util.List<StudentSubjectAttendanceSummary> findByClassRoomAndSubject(ClassRoom classRoom, Subject subject);
	
}
