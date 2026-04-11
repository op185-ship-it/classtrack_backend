package com.classtrack.service;

import org.springframework.stereotype.Service;

import com.classtrack.dto.request.TeacherRequestDto;
import com.classtrack.dto.response.TeacherResponseDto;
import com.classtrack.entity.Department;
import com.classtrack.entity.Teacher;
import com.classtrack.repository.DepartmentRepository;
import com.classtrack.repository.TeacherRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final DepartmentRepository departmentRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository,
                              DepartmentRepository departmentRepository) {
        this.teacherRepository = teacherRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public TeacherResponseDto createTeacher(TeacherRequestDto dto) {

        Department department = departmentRepository
                .findByDepartmentCode(dto.getDepartmentCode())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Teacher teacher = new Teacher();
        teacher.setName(dto.getName());
        teacher.setEmail(dto.getEmail());
        teacher.setPassword(dto.getPassword());
        teacher.setDepartment(department);
        teacher.setEmployeeId(dto.getEmployeeId());
        teacherRepository.save(teacher);

        return new TeacherResponseDto(
                teacher.getName(),
                teacher.getEmail(),
                department.getDepartmentName()
        );
    }
}
