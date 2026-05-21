package com.classtrack.repository;

import java.util.UUID;

import com.classtrack.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import com.classtrack.entity.Student;

public interface StudentRepository extends JpaRepository<Student, UUID> {
        long countByClassRoom(ClassRoom classRoom);
}
