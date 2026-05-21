package com.classtrack.repository;

import java.util.Optional;
import java.util.UUID;

import com.classtrack.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.classtrack.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
	
	Optional<User> findByEmailAndPassword(String email,String password);

    @Query("SELECT COUNT(u) FROM Student u WHERE u.department = :department")
    long countStudentsByDepartment(@Param("department")Department department);

    @Query("SELECT COUNT(u) FROM Teacher u WHERE u.department = :department")
    long countTeachersByDepartment(@Param("department")Department department);
}
