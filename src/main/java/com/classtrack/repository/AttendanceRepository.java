package com.classtrack.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.classtrack.entity.Attendance;
import com.classtrack.entity.AttendanceStatus;
import com.classtrack.entity.LectureSession;
import com.classtrack.entity.Student;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {

	List<Attendance> findByStudent(Student student);

	List<Attendance> findByLectureSession(LectureSession lectureSession);

	long countByLectureSession(LectureSession lectureSession);

	Optional<Attendance> findByStudentAndLectureSession(Student student, LectureSession lectureSession);

	long countByLectureSessionAndAttendaceStatus(LectureSession lectureSession, AttendanceStatus attendaceStatus);
	
	
}
