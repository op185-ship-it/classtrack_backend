package com.classtrack.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.classtrack.entity.Student;

public interface StudentRepository extends JpaRepository<Student, UUID> {

}
