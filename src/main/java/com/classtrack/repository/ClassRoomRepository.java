package com.classtrack.repository;

import com.classtrack.entity.Department;
import com.classtrack.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import com.classtrack.entity.ClassRoom;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {

	Optional<ClassRoom> findByClassRoomName(String classRoomName);

    long countByDepartment(Department department);

//    List<Student> findStudentByClassRoom(ClassRoom classRoom);
}
