package com.classtrack.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.classtrack.dto.request.ScheduleRequestDto;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import com.classtrack.dto.ScheduleDto;
import com.classtrack.dto.response.ScheduleResponseDto;
import com.classtrack.entity.ClassRoom;
import com.classtrack.entity.Schedule;
import com.classtrack.entity.Student;
import com.classtrack.entity.Subject;
import com.classtrack.entity.Teacher;
import com.classtrack.repository.ClassRoomRepository;
import com.classtrack.repository.ScheduleRepository;
import com.classtrack.repository.StudentRepository;
import com.classtrack.repository.SubjectRepository;
import com.classtrack.repository.TeacherRepository;

import jakarta.transaction.Transactional;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	
	StudentRepository studentRepository;
	TeacherRepository teacherRepository;
	SubjectRepository subjectRepository;
	ClassRoomRepository classRoomRepository;
	ScheduleRepository scheduleRepository;


public ScheduleServiceImpl(StudentRepository studentRepository, TeacherRepository teacherRepository,
			SubjectRepository subjectRepository, ClassRoomRepository classRoomRepository,
			ScheduleRepository scheduleRepository) {
		super();
		this.studentRepository = studentRepository;
		this.teacherRepository = teacherRepository;
		this.subjectRepository = subjectRepository;
		this.classRoomRepository = classRoomRepository;
		this.scheduleRepository = scheduleRepository;
	}
//for testing purpose this service returns the whole schedule of a particular class
	@Override
	@Transactional
	public List<ScheduleResponseDto> createSchehdule(ScheduleDto scheduleDto) {
		//check if teacher is free  or not for this new schedule entry
		List<Schedule> clashes = scheduleRepository
				.findClashingScedulesList(
						scheduleDto.getTeacherId(), 
						scheduleDto.getDayOfWeek(),
						scheduleDto.getStartTime(), 
						scheduleDto.getEndTime()
						);
		if(!clashes.isEmpty()) {
			throw new RuntimeException("teacher is busy during that time");	
		}
		//if the teacher is free then start the process of if class is free or not
		
		 if (!scheduleRepository.findClashingSchedulesByClassRoom(
				 scheduleDto.getClassRoomId(), scheduleDto.getDayOfWeek(),
		            scheduleDto.getStartTime(),scheduleDto.getEndTime()).isEmpty()) {
		        throw new RuntimeException("Classroom is occupied at this time!");
		    }
		
		
		
		
		// find the subject
		Subject subject = subjectRepository
				.findById(scheduleDto.getSubjectId())
				.orElseThrow(()-> new RuntimeException("subject not found"));
		
		//find the teacher
		Teacher teacher = teacherRepository
				.findById(scheduleDto.getTeacherId())
				.orElseThrow(()-> new RuntimeException("Teacher not found"));
		
		
		
		
		
		//find the classRoom
		ClassRoom classRoom = classRoomRepository
				.findById(scheduleDto.getClassRoomId())
				.orElseThrow(()-> new RuntimeException("ClassRoom not found"));	
		
		//create the Schedule object, assign values
		Schedule schedule = new Schedule();
		schedule.setClassRoom(classRoom);
		schedule.setSubject(subject);
		schedule.setTeacher(teacher);
		schedule.setDayOfWeek(scheduleDto.getDayOfWeek());
		schedule.setStartTime(scheduleDto.getStartTime());
		schedule.setEndTime(scheduleDto.getEndTime());
		
		//persist
		scheduleRepository.save(schedule);
		classRoomRepository.save(classRoom);
		//generate DTO
		List<ScheduleResponseDto> li = new ArrayList<>();
		
		for(Schedule s : classRoom.getSchedules()) {
			ScheduleResponseDto dto = new ScheduleResponseDto();
			dto.setSubjectName(s.getSubject().getSubjectCode());
			dto.setTeacherName(s.getTeacher().getName());
			dto.setDayOfWeek(s.getDayOfWeek());
			dto.setStartTime(s.getStartTime());
			dto.setEndTime(s.getEndTime());
			li.add(dto);
		}
		
		return li;
	}
	@Override
	public List<ScheduleResponseDto> getClassRoomScheduleForStudent(UUID userId) {
		// get the Student
		Student student = studentRepository.findById(userId).orElseThrow();
		
		List<ScheduleResponseDto> li = new ArrayList<>();
		
		for(Schedule s : student.getClassRoom().getSchedules()) {
			ScheduleResponseDto dto = new ScheduleResponseDto();
			dto.setSubjectName(s.getSubject().getSubjectCode());
			dto.setSubjectCode(s.getSubject().getSubjectCode());
			dto.setTeacherName(s.getTeacher().getName());
			dto.setDayOfWeek(s.getDayOfWeek());
			dto.setStartTime(s.getStartTime());
			dto.setEndTime(s.getEndTime());
			li.add(dto);
		}
		
		return li;
	}

    @Override
    public @Nullable List<ScheduleResponseDto> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<ScheduleResponseDto> responseDtos = new ArrayList<>();

        for(Schedule s : schedules){
            ScheduleResponseDto dto = new ScheduleResponseDto();
            dto.setSubjectName(s.getSubject().getSubjectName());
            dto.setSubjectCode(s.getSubject().getSubjectCode());
            dto.setTeacherName(s.getTeacher().getName());
            dto.setDayOfWeek(s.getDayOfWeek());
            dto.setStartTime(s.getStartTime());
            dto.setEndTime(s.getEndTime());
            dto.setClassRoomName(s.getClassRoom().getClassRoomName());
            System.out.println(s.getClassRoom().getClassRoomName());
            responseDtos.add(dto);
        }
        return  responseDtos;
    }

    @Override
    public @Nullable String createSchehduleNew(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule();

        schedule.setClassRoom(classRoomRepository.findByClassRoomName(scheduleRequestDto.getClassRoomName()).orElseThrow(()-> new RuntimeException("ClassRoom not found")));

        schedule.setSubject(subjectRepository.findBySubjectName(scheduleRequestDto.getSubjectName()).orElseThrow(()-> new RuntimeException("subject not found")));

        schedule.setTeacher(teacherRepository.findByName(scheduleRequestDto.getTeacherName()).orElseThrow(()-> new RuntimeException("Teacher not found")));

        schedule.setStartTime(scheduleRequestDto.getStartTime());
        schedule.setEndTime(scheduleRequestDto.getEndTime());
        schedule.setDayOfWeek(scheduleRequestDto.getDayOfWeek());

        schedule= scheduleRepository.save(schedule);
        return  "schedule created successfully";
    }

}
