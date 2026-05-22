package com.classtrack.service;

import com.classtrack.dto.response.ScheduleResponseDto;
import com.classtrack.entity.Schedule;
import com.classtrack.repository.ScheduleRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.UUID;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    ScheduleRepository scheduleRepository;

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

    @Override
    public @Nullable List<ScheduleResponseDto> getAllSchedules(UUID userId) {
        List<Schedule> schedules = scheduleRepository.findAllByTeacher(teacherRepository.findById(userId).orElseThrow(()-> new RuntimeException("teacher not found")));
        List<ScheduleResponseDto> responseDtos = new ArrayList<>();
        for(Schedule s : schedules){
            ScheduleResponseDto dto = new ScheduleResponseDto();
            dto.setClassRoomName(s.getClassRoom().getClassRoomName());
            dto.setTeacherName(s.getTeacher().getName());
            dto.setSubjectName(s.getSubject().getSubjectName());
            dto.setDayOfWeek(s.getDayOfWeek());
            dto.setStartTime(s.getStartTime());
            dto.setEndTime(s.getEndTime());
            dto.setSubjectCode(s.getSubject().getSubjectCode());
            dto.setScheduleId(s.getId());
            responseDtos.add(dto);
        }
        return  responseDtos;
    }
}
