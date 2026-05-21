package com.classtrack.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.classtrack.entity.Department;
import java.util.Optional;


public interface DepartmentRepository extends JpaRepository<Department, UUID> {

	Optional<Department> findByDepartmentCode(String departmentCode);


	
}
