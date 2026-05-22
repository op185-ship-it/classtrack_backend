package com.classtrack.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.classtrack.dto.request.TeacherRequestDto;
import com.classtrack.dto.response.TeacherResponseDto;
import com.classtrack.entity.Department;
import com.classtrack.entity.Teacher;
import com.classtrack.repository.DepartmentRepository;
import com.classtrack.repository.TeacherRepository;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<?> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        List<TeacherResponseDto> responseDtos = new ArrayList<>();
        for (Teacher t : teachers){
            TeacherResponseDto dto = new TeacherResponseDto(t.getName(),t.getEmail(),t.getDepartment().getDepartmentName());
            responseDtos.add(dto);
        }

        return responseDtos;
    }
}
