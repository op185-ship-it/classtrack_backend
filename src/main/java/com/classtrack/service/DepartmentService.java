package com.classtrack.service;

import java.util.List;

import com.classtrack.entity.Department;

public interface DepartmentService {

	Department createDepartment(Department department);

	List<Department> getAllDepartments();
}
