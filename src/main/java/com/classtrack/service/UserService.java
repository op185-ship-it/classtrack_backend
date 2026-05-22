package com.classtrack.service;

import com.classtrack.dto.ClassRoomResponseDto;
import com.classtrack.dto.response.AdminManagePageResponseDto;
import com.classtrack.dto.response.DepartmentResponseDto;
import com.classtrack.dto.response.SubjectResponseDto;
import com.classtrack.entity.*;
import com.classtrack.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.classtrack.dto.UserResponseDto;
import com.classtrack.dto.request.LoginRequestDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    ClassRoomRepository classRoomRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    SubjectRepository subjectRepository;



	
	public UserResponseDto loginUser(LoginRequestDto dto) {
		User user = userRepository
				.findByEmailAndPassword(dto.getEmail(), dto.getPassword())
				.orElseThrow(()->new RuntimeException("no user found"));
		
		UserResponseDto dto2 = new UserResponseDto();
		if(user instanceof Teacher) {
			dto2.setRole("teacher");
		}else if(user instanceof Student) {
			dto2.setRole("student");
		}
		dto2.setName(user.getName());
		dto2.setEmail(user.getEmail());
		dto2.setUserId(user.getId());
		return dto2;
		}

    public AdminManagePageResponseDto getManagePageData() {
            long countOfDepartments = departmentRepository.count();
            long countOfClassrooms = classRoomRepository.count();
            long countOfStudents = studentRepository.count();
            long countOfTeachers = teacherRepository.count();

            List<DepartmentResponseDto> departmentsDto = new ArrayList<>();
            List<ClassRoomResponseDto> classroomsDto = new ArrayList<>();
        List<SubjectResponseDto> subjectsDto = new ArrayList<>();

            List<Department> departments = departmentRepository.findAll();
            for(Department d : departments){
                DepartmentResponseDto dto = new DepartmentResponseDto();
                dto.setDepartmentName(d.getDepartmentName());
                dto.setDepartmentCode(d.getDepartmentCode());
                dto.setNoOfClasses(classRoomRepository.countByDepartment(d));
                dto.setNoOfStudents(userRepository.countStudentsByDepartment(d));
                dto.setNoOfTeachers(userRepository.countTeachersByDepartment(d));
                dto.setNoOfClasses(classRoomRepository.countByDepartment(d));

                departmentsDto.add(dto);
            }

            List<ClassRoom> classRooms = classRoomRepository.findAll();
            for(ClassRoom c : classRooms){
                ClassRoomResponseDto dto = new ClassRoomResponseDto();
                dto.setSem(c.getSemester());
                dto.setDepartmentName(c.getDepartment().getDepartmentCode());
                dto.setClassRoomName(c.getClassRoomName());
                dto.setNoOfStudents(studentRepository.countByClassRoom(c));
                classroomsDto.add(dto);
            }

            List<Subject> subjects = subjectRepository.findAll();

             for(Subject s : subjects){
            SubjectResponseDto dto = new SubjectResponseDto();
            dto.setSubjectNameAndCode(s.getSubjectName()+"("+s.getSubjectCode()+")");
            dto.setDepartmentNameAndCode(s.getDepartment().getDepartmentName()+"("+s.getDepartment().getDepartmentCode()+")");
            dto.setSemester(s.getSemester());
                 System.out.println(dto.getDepartmentNameAndCode());
            subjectsDto.add(dto);
        }


            return new AdminManagePageResponseDto(countOfDepartments,countOfClassrooms,countOfStudents,countOfTeachers,departmentsDto,classroomsDto,subjectsDto);
    }
}
