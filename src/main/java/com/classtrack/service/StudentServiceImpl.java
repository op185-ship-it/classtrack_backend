package com.classtrack.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.classtrack.dto.request.StudentRequestDto;
import com.classtrack.dto.response.AttendanceSummaryPerSubjectDto;
import com.classtrack.dto.response.ScheduleResponseDto;
import com.classtrack.dto.response.StudentHomePageResponseDto;
import com.classtrack.dto.response.StudentResponseDto;
import com.classtrack.dto.response.StudentSessionResponseDto;
import com.classtrack.entity.Attendance;
import com.classtrack.entity.ClassRoom;
import com.classtrack.entity.Department;
import com.classtrack.entity.LectureSession;
import com.classtrack.entity.Schedule;
import com.classtrack.entity.Student;
import com.classtrack.entity.StudentSubjectAttendanceSummary;
import com.classtrack.entity.Subject;
import com.classtrack.repository.AttendanceRepository;
import com.classtrack.repository.ClassRoomRepository;
import com.classtrack.repository.DepartmentRepository;
import com.classtrack.repository.LectureSessionRepository;
import com.classtrack.repository.ScheduleRepository;
import com.classtrack.repository.StudentRepository;
import com.classtrack.repository.StudentSubjectAttendanceSummaryRepository;
import com.classtrack.repository.SubjectRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentServiceImpl implements StudentService {
	
	StudentRepository studentRepository;
	DepartmentRepository departmentRepository;
	ClassRoomRepository classRoomRepository;
	LectureSessionRepository lectureSessionRepository;
	AttendanceRepository attendanceRepository;
	StudentSubjectAttendanceSummaryRepository attendanceSummaryRepository;
	ScheduleRepository scheduleRepository;
	StudentSubjectAttendanceSummaryRepository summaryRepository;
	SubjectRepository subjectRepository;


	public StudentServiceImpl(StudentRepository studentRepository, DepartmentRepository departmentRepository,
			ClassRoomRepository classRoomRepository, LectureSessionRepository lectureSessionRepository,
			AttendanceRepository attendanceRepository,
			StudentSubjectAttendanceSummaryRepository attendanceSummaryRepository,
			ScheduleRepository scheduleRepository, StudentSubjectAttendanceSummaryRepository summaryRepository,
			SubjectRepository subjectRepository) {
		super();
		this.studentRepository = studentRepository;
		this.departmentRepository = departmentRepository;
		this.classRoomRepository = classRoomRepository;
		this.lectureSessionRepository = lectureSessionRepository;
		this.attendanceRepository = attendanceRepository;
		this.attendanceSummaryRepository = attendanceSummaryRepository;
		this.scheduleRepository = scheduleRepository;
		this.summaryRepository = summaryRepository;
		this.subjectRepository = subjectRepository;
	}

	@Override
	@Transactional
	public StudentResponseDto createStudent(StudentRequestDto dto) {
		// fetch department
		Department department = departmentRepository
				.findByDepartmentCode(dto.getDepartmentCode())
				.orElseThrow(()-> new RuntimeException("No department found"));
		
		//fetch the classroom
		ClassRoom classRoom = classRoomRepository
				.findByClassRoomName(dto.getClassRoomName())
				.orElseThrow(()-> new RuntimeException("Classroom not found"));
		
		//create the student and assign department and classroom
		Student student = new Student();
		student.setName(dto.getName());
		student.setEmail(dto.getEmail());
		student.setPassword(dto.getPassword());
		student.setRollNumber(dto.getRollNumber());
		student.setClassRoom(classRoom);
		student.setDepartment(department);
		
		//save the student
		studentRepository.save(student);
		return new StudentResponseDto(
				student.getName(),
				student.getEmail(),
				student.getClassRoom().getClassRoomName(),
				student.getDepartment().getDepartmentCode(),
				student.getClassRoom().getSemester()
				);
	}

	@Override
	public StudentHomePageResponseDto homePageResponse(UUID userId) {
		// find the student
		Student student = studentRepository
				.findById(userId)
				.orElseThrow();
		
		StudentHomePageResponseDto responseDto = new StudentHomePageResponseDto();
		responseDto.setRollNumber(student.getRollNumber());
		responseDto.setClassRoomName(student.getClassRoom().getClassRoomName());
		
		Long classesHeld = attendanceSummaryRepository.countClassesHeldByClassRoom(student.getClassRoom());
		responseDto.setClassesHeld(classesHeld);
		
		long classesAttended = attendanceSummaryRepository.countClassesAttendedByStudent(student);
		responseDto.setClassesAttended(classesAttended);
		
		DayOfWeek today= LocalDate.now().getDayOfWeek();
		
		List<Schedule> daySchedules = scheduleRepository.findByDayOfWeek(today);
		
		for(Schedule s : daySchedules) {
			ScheduleResponseDto dto = new ScheduleResponseDto();
			dto.setSubjectName(s.getSubject().getSubjectName());
			dto.setSubjectCode(s.getSubject().getSubjectCode());
			dto.setTeacherName(s.getTeacher().getName());
			dto.setStartTime(s.getStartTime());
			dto.setEndTime(s.getEndTime());
			dto.setDayOfWeek(s.getDayOfWeek());
			responseDto.addTodaysClasses(dto);			
		}
		
		List<Attendance> recentSessions = attendanceRepository.findByStudent(student);
		for(Attendance a : recentSessions) {
			StudentSessionResponseDto dto = new StudentSessionResponseDto();
			dto.setAttendanceStatus(a.getAttendaceStatus());
			dto.setDateOfSession(a.getMakredAt().toLocalDate());
			dto.setSubjectName(a.getLectureSession().getSubject().getSubjectName());
			responseDto.addRecentSessions(dto);
		}
		
		return responseDto;
	}

	@Override
	public List<StudentSessionResponseDto> getAllSessionsByStudentId(UUID uuid) {
		// find the attendance list(session list) by student Id
		Student student = studentRepository.findById(uuid).orElseThrow();
		List<Attendance> recentSessions = attendanceRepository.findByStudent(student);
		
		List<StudentSessionResponseDto> responseDto = new ArrayList<>();
		for(Attendance a : recentSessions) {
			StudentSessionResponseDto dto = new StudentSessionResponseDto();
			dto.setAttendanceStatus(a.getAttendaceStatus());
			dto.setDateOfSession(a.getMakredAt().toLocalDate());
			dto.setSubjectName(a.getLectureSession().getSubject().getSubjectName());
			responseDto.add(dto);
		}
		
		return responseDto;
	}

	@Override
	public List<AttendanceSummaryPerSubjectDto> getSubjectWiseAttendanceByStudentId(UUID userId) {
		// get the subjects for that students class
		Student student = studentRepository.findById(userId).orElseThrow();
		Integer sem = studentRepository.findById(userId).orElseThrow().getClassRoom().getSemester();
		List<Subject> subjects = subjectRepository.findBySemester(sem);
		
		List<AttendanceSummaryPerSubjectDto> responseDto = new ArrayList<>();
		
		for(Subject s : subjects) {
			AttendanceSummaryPerSubjectDto dto = new AttendanceSummaryPerSubjectDto();
			
			StudentSubjectAttendanceSummary summary = summaryRepository
					.findByStudentAndSubjectAndClassRoom(student, s, student.getClassRoom())
					.orElseGet(() -> {
				        // First time this student has a record for this subject+classroom
				        StudentSubjectAttendanceSummary newSummary = new StudentSubjectAttendanceSummary();
				        newSummary.setStudent(student);
				        newSummary.setSubject(s);
				        newSummary.setClassRoom(student.getClassRoom());
				        newSummary.setClassesHeld(0L);
				        newSummary.setClassesAttended(0L);
				        return newSummary;
				    });
			
			dto.setSubjectname(summary.getSubject().getSubjectName());
			dto.setSubjectCode(summary.getSubject().getSubjectCode());
			dto.setClassesAttended(summary.getClassesAttended());
			dto.setClassesHeld(summary.getClassesHeld());
			if(summary.getClassesHeld() == 0) {
				
				dto.setPercentage(0L);
			}else {
				dto.setPercentage((summary.getClassesAttended()/summary.getClassesHeld())*100);
			}
			responseDto.add(dto);
		}
		
		return responseDto;
	}

}
