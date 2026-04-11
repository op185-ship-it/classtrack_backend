package com.classtrack.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.classtrack.entity.Department;
import com.classtrack.repository.DepartmentRepository;

import jakarta.transaction.Transactional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	DepartmentRepository departmentRepository;
	
	@Override
	@Transactional
	public Department createDepartment(Department department) {
		
		Department d = departmentRepository.save(department);		
		return d;
	}

	@Override
	public List<Department> getAllDepartments() {
		// TODO Auto-generated method stub
		return departmentRepository.findAll();
	}


}
