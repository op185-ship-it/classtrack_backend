package com.classtrack.service;

import java.util.*;

import com.classtrack.dto.response.StudentResponseDto;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import com.classtrack.dto.ClassRoomCreateDto;
import com.classtrack.dto.ClassRoomEnrollDto;
import com.classtrack.dto.ClassRoomResponseDto;
import com.classtrack.dto.UserResponseDto;
import com.classtrack.dto.response.ScheduleResponseDto;
import com.classtrack.entity.ClassRoom;
import com.classtrack.entity.Department;
import com.classtrack.entity.Student;
import com.classtrack.entity.User;
import com.classtrack.repository.ClassRoomRepository;
import com.classtrack.repository.DepartmentRepository;
import com.classtrack.repository.StudentRepository;
import com.classtrack.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ClassRoomServiceImpl implements ClassRoomService {

	DepartmentRepository departmentRepository;
	ClassRoomRepository classRoomRepository;
	UserRepository userRepository;
	StudentRepository studentRepository;



	public ClassRoomServiceImpl(DepartmentRepository departmentRepository, ClassRoomRepository classRoomRepository,
			UserRepository userRepository, StudentRepository studentRepository) {
		super();
		this.departmentRepository = departmentRepository;
		this.classRoomRepository = classRoomRepository;
		this.userRepository = userRepository;
		this.studentRepository = studentRepository;
	}



	@Transactional
	@Override
	public Map<String, String> createClassRoom(ClassRoomCreateDto dto) {
		System.out.println(dto);
		Department d = departmentRepository.findByDepartmentCode(dto.getDepartmentCode())
				.orElseThrow(()->new RuntimeException("Dept not found"));
		
		ClassRoom classRoom = new ClassRoom();
		classRoom.setClassRoomName(dto.getClassRoomName());
		classRoom.setDepartment(d);
		classRoom.setSemester(dto.getSemester());
		
		d.getClassRooms().add(classRoom);
		d= departmentRepository.save(d);
		
		Map<String, String> hm = new HashMap<>();
		
		for(ClassRoom c : d.getClassRooms()) {
			hm.put(c.getClassRoomName(), c.getSemester().toString());
		}
		System.out.println(hm);
		return hm;
	}

    @Override
    public @Nullable List<StudentResponseDto> findAllStudentsByClassRoomName(String classRoomName) {
        ClassRoom classRoom = classRoomRepository.findByClassRoomName(classRoomName).orElseThrow(()-> new RuntimeException("no classroom found with that name"));
        List<Student> students = studentRepository.findByClassRoom(classRoom);
        List<StudentResponseDto> responseDtos = new ArrayList<>();
        for(Student s : students){
           StudentResponseDto dto = new StudentResponseDto();
           dto.setName(s.getName());
           dto.setUserId(s.getId());
           dto.setRollNumber(s.getRollNumber());
           dto.setEnrolledClass(s.getClassRoom().getClassRoomName());
           dto.setDepartment(s.getDepartment().getDepartmentCode());
           dto.setEmail(s.getEmail());
           dto.setSemester(s.getClassRoom().getSemester());
           responseDtos.add(dto);
        }
        return  responseDtos;
    }


//	@Override
//	@Transactional
//	public ClassRoomResponseDto enrollClass(String classRoomName, ClassRoomEnrollDto dto) {
//		User user = userRepository.findById(dto.getUserId())
//				.orElseThrow(()-> new RuntimeException("user not found"));
//		
////		System.out.println(user);
//		ClassRoom classRoom = classRoomRepository.findByClassRoomName(classRoomName)
//				.orElseThrow(()-> new RuntimeException("classroom not found"));
//		
//		classRoom.addStudents(user);
//		classRoomRepository.save(classRoom);
//		
//		ClassRoomResponseDto cDto = new ClassRoomResponseDto();
//		cDto.setClassRoomName(classRoom.getClassRoomName());
//		cDto.setDepartmentName(classRoom.getDepartment().getDepartmentName());
//		
//		for(User c : classRoom.getUsers()) {
//			UserResponseDto userResponseDto = new UserResponseDto();
//			userResponseDto.setName(c.getName());
//			userResponseDto.setEmail(c.getEmail());
//			userResponseDto.setRole(c.getRole().toString());
//			cDto.addStudent(userResponseDto);
//		}
//		
//		return cDto;
//	}

}
