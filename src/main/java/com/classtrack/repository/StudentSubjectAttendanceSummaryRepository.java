package com.classtrack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
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
	
	Long countClassesHeldByClassRoom(ClassRoom classRoom);
	
	Long countClassesAttendedByStudent(Student student);
	
}
