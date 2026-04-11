package com.classtrack.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.classtrack.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

}
