package com.classtrack.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.classtrack.dto.response.*;
import com.classtrack.dto.request.FlaskAttendanceMarkRequestDto;
import com.classtrack.repository.*;
import org.springframework.stereotype.Service;

import com.classtrack.dto.request.AttendanceRequestDto;
import com.classtrack.entity.AttendanceStatus;
import com.classtrack.entity.Attendance;
import com.classtrack.entity.ClassRoom;
import com.classtrack.entity.LectureSession;
import com.classtrack.entity.Schedule;
import com.classtrack.entity.SessionStatus;
import com.classtrack.entity.Student;
import com.classtrack.entity.StudentSubjectAttendanceSummary;
import com.classtrack.entity.Subject;

import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	LectureSessionRepository lectureSessionRepository;
	ScheduleRepository scheduleRepository;
	AttendanceRepository attendanceRepository;
	ClassRoomRepository classRoomRepository;
	SubjectRepository subjectRepository;
	StudentSubjectAttendanceSummaryRepository attendanceSummaryRepository;
	

	public AttendanceServiceImpl(LectureSessionRepository lectureSessionRepository,
			ScheduleRepository scheduleRepository, AttendanceRepository attendanceRepository,
			ClassRoomRepository classRoomRepository, SubjectRepository subjectRepository,
			StudentSubjectAttendanceSummaryRepository attendanceSummaryRepository) {
		super();
		this.lectureSessionRepository = lectureSessionRepository;
		this.scheduleRepository = scheduleRepository;
		this.attendanceRepository = attendanceRepository;
		this.classRoomRepository = classRoomRepository;
		this.subjectRepository = subjectRepository;
		this.attendanceSummaryRepository = attendanceSummaryRepository;
	}


	@Override
	@Transactional
	public ClassStartedResponseDto startClass(Long scheduleId) {
		//find the schedule
		Schedule schedule = scheduleRepository
				.findById(scheduleId)
				.orElseThrow(()-> new RuntimeException("schedule not found"));
		
		//create the session
		LectureSession session = new LectureSession();
		session.setDateOfSession(LocalDate.now());
		session.setSchedule(schedule);
		session.setSessionStatus(SessionStatus.STARTED);
		session.setSubject(schedule.getSubject());
		session.setClassRoom(schedule.getClassRoom());
		
		//save the session
		session = lectureSessionRepository.save(session);
		
		ClassStartedResponseDto dto = new ClassStartedResponseDto();
		dto.setSessionId(session.getId());
		dto.setMessage("Class has started at: "+LocalDateTime.now());
		return dto;
	}


	@Override
	@Transactional
	public TeacherAttendanceResponseDto markAttendance(AttendanceRequestDto requestDto) {
		if (requestDto == null || requestDto.getSessionId() == null) {
			throw new RuntimeException("sessionId is required");
		}

		// find session
		LectureSession lectureSession = lectureSessionRepository
				.findById(requestDto.getSessionId())
				.orElseThrow(() -> new RuntimeException("no session found"));

		// idempotency: if attendance already saved for this session, return existing result
		if (attendanceRepository.countByLectureSession(lectureSession) > 0) {
			return buildResponseFromExistingAttendance(lectureSession);
		}

		// only allow marking when session is STARTED
		if (lectureSession.getSessionStatus() != SessionStatus.STARTED) {
			throw new RuntimeException("session is not active");
		}
		
		//find classroom
		ClassRoom classRoom = lectureSession.getSchedule().getClassRoom();
		
		//find all the students
		List<Student>  students = classRoom.getStudents();
		
		// save the attendance map (null-safe)
		Map<Integer, AttendanceStatus> hm = requestDto.getAttendanceReport() != null
				? requestDto.getAttendanceReport() : Map.of();
		
		TeacherAttendanceResponseDto responseDto = new TeacherAttendanceResponseDto();
		responseDto.setSessionId(lectureSession.getId());
		responseDto.setClasRoomName(classRoom.getClassRoomName());
		responseDto.setDateOfLecture(lectureSession.getDateOfSession());
		responseDto
		.setSubjectNameAndCode(lectureSession.getSchedule().getSubject().getSubjectName()+"("+
				lectureSession.getSchedule().getSubject().getSubjectCode()+")"
				);
		
		List<AttendanceEntryDto> li = new ArrayList<>();
		
		//mark their attendance and also update the summary table(also create the dto in this loop
			for(Student s : students) {
				
				Attendance attendance = new Attendance();
//				BasicStudentDto basicStudentDto = new BasicStudentDto();
				AttendanceEntryDto dto = new AttendanceEntryDto();
				
				dto.setStudentName(s.getName());
				dto.setRollNumber(s.getRollNumber());
				
				
				attendance.setStudent(s);
				attendance.setLectureSession(lectureSession);
				attendance.setMakredAt(LocalDateTime.now());
				//check if the student roll and present matches mark it present
				if(hm.get(s.getRollNumber()) == AttendanceStatus.PRESENT) {
					attendance.setAttendaceStatus(AttendanceStatus.PRESENT);
				}else {
					//else mark them absent
					attendance.setAttendaceStatus(AttendanceStatus.ABSENT);
				}
				attendance=attendanceRepository.save(attendance);
				
				//for updating the summery table
				// --- Summary update ---
				Subject subject = lectureSession.getSubject();

				StudentSubjectAttendanceSummary summary = attendanceSummaryRepository
				    .findByStudentAndSubjectAndClassRoom(s, subject, classRoom)
				    .orElseGet(() -> {
				        // First time this student has a record for this subject+classroom
				        StudentSubjectAttendanceSummary newSummary = new StudentSubjectAttendanceSummary();
				        newSummary.setStudent(s);
				        newSummary.setSubject(subject);
				        newSummary.setClassRoom(classRoom);
				        newSummary.setClassesHeld(0L);
				        newSummary.setClassesAttended(0L);
				        return newSummary;
				    });

				// Always increment classesHeld (a lecture happened)
				summary.setClassesHeld(summary.getClassesHeld() + 1);

				// Only increment classesAttended if they were PRESENT
				if (attendance.getAttendaceStatus() == AttendanceStatus.PRESENT) {
				    summary.setClassesAttended(summary.getClassesAttended() + 1);
				}

				attendanceSummaryRepository.save(summary);

//				lectureSession.setSessionStatus(SessionStatus.COMPLETED);
				
				
				dto.setStatus(attendance.getAttendaceStatus());
				li.add(dto);
				
			}
			//mark the class as completed
			lectureSession.setSessionStatus(SessionStatus.COMPLETED);
			lectureSessionRepository.save(lectureSession);
			responseDto.setAttendanceResult(li);
		
		return responseDto;
	}

	private TeacherAttendanceResponseDto buildResponseFromExistingAttendance(LectureSession lectureSession) {
		ClassRoom classRoom = lectureSession.getSchedule().getClassRoom();

		TeacherAttendanceResponseDto responseDto = new TeacherAttendanceResponseDto();
		responseDto.setSessionId(lectureSession.getId());
		responseDto.setClasRoomName(classRoom.getClassRoomName());
		responseDto.setDateOfLecture(lectureSession.getDateOfSession());
		responseDto.setSubjectNameAndCode(
				lectureSession.getSchedule().getSubject().getSubjectName() + "("
						+ lectureSession.getSchedule().getSubject().getSubjectCode() + ")"
		);

		List<AttendanceEntryDto> li = new ArrayList<>();
		List<Attendance> existing = attendanceRepository.findByLectureSession(lectureSession);
		for (Attendance a : existing) {
			AttendanceEntryDto dto = new AttendanceEntryDto();
			dto.setStudentName(a.getStudent().getName());
			dto.setRollNumber(a.getStudent().getRollNumber());
			dto.setStatus(a.getAttendaceStatus());
			li.add(dto);
		}
		responseDto.setAttendanceResult(li);
		return responseDto;
	}

	@Override
	@Transactional
	public TeacherAttendanceResponseDto markAttendanceFromFlask(FlaskAttendanceMarkRequestDto requestDto) {
		if (requestDto == null || requestDto.getSessionId() == null) {
			throw new RuntimeException("sessionId is required");
		}

		Map<Integer, AttendanceStatus> hm = new HashMap<>();
		if (requestDto.getPresentRollNumbers() != null) {
			for (Integer roll : requestDto.getPresentRollNumbers()) {
				if (roll != null) {
					hm.put(roll, AttendanceStatus.PRESENT);
				}
			}
		}

		AttendanceRequestDto teacherRequest = new AttendanceRequestDto();
		teacherRequest.setSessionId(requestDto.getSessionId());
		teacherRequest.setAttendanceReport(hm);

		return markAttendance(teacherRequest);
	}

    @Override
    public void markAttendanceSummary(String classRoomName) {

    }


}
