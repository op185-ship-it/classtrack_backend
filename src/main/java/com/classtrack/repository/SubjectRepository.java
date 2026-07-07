package com.classtrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.classtrack.entity.Subject;
import java.util.List;
import java.util.Optional;


public interface SubjectRepository extends JpaRepository<Subject, Long> {
	
	List<Subject> findBySemester(Integer semester);


    Optional<Subject> findBySubjectName(String subjectName);
}
