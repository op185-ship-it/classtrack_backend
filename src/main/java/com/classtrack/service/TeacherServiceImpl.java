package com.classtrack.service;

import com.classtrack.dto.response.ScheduleResponseDto;
import com.classtrack.dto.response.TeacherAttendanceResponseDto;
import com.classtrack.dto.response.TeacherReportDto;
import com.classtrack.dto.response.TeacherReportStudentDto;
import com.classtrack.dto.response.TeacherSessionDto;
import com.classtrack.entity.AttendanceStatus;
import com.classtrack.entity.LectureSession;
import com.classtrack.entity.Schedule;
import com.classtrack.entity.StudentSubjectAttendanceSummary;
import com.classtrack.entity.Subject;
import com.classtrack.repository.ScheduleRepository;
import com.classtrack.repository.AttendanceRepository;
import com.classtrack.repository.LectureSessionRepository;
import com.classtrack.repository.StudentSubjectAttendanceSummaryRepository;
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
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    LectureSessionRepository lectureSessionRepository;

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    StudentSubjectAttendanceSummaryRepository attendanceSummaryRepository;

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

    @Override
    public List<TeacherSessionDto> getPastSessions(UUID teacherId) {
        List<LectureSession> sessions = lectureSessionRepository.findAllByTeacherIdOrderByDateDesc(teacherId);
        List<TeacherSessionDto> dtos = new ArrayList<>();
        for (LectureSession ls : sessions) {
            TeacherSessionDto dto = new TeacherSessionDto();
            dto.setSessionId(ls.getId());
            dto.setScheduleId(ls.getSchedule() != null ? ls.getSchedule().getId() : null);
            dto.setClassRoomName(ls.getClassRoom() != null ? ls.getClassRoom().getClassRoomName() : null);
            dto.setSubjectName(ls.getSubject() != null ? ls.getSubject().getSubjectName() : null);
            dto.setSubjectCode(ls.getSubject() != null ? ls.getSubject().getSubjectCode() : null);
            dto.setDateOfSession(ls.getDateOfSession());
            dto.setSessionStatus(ls.getSessionStatus());

            long present = attendanceRepository.countByLectureSessionAndAttendaceStatus(ls, AttendanceStatus.PRESENT);
            long absent = attendanceRepository.countByLectureSessionAndAttendaceStatus(ls, AttendanceStatus.ABSENT);
            dto.setPresentCount(present);
            dto.setAbsentCount(absent);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public TeacherAttendanceResponseDto getSessionDetails(UUID sessionId) {
        LectureSession lectureSession = lectureSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("no session found"));

        // reuse AttendanceServiceImpl's response shape by reconstructing from stored attendance rows
        TeacherAttendanceResponseDto responseDto = new TeacherAttendanceResponseDto();
        responseDto.setSessionId(lectureSession.getId());
        responseDto.setClasRoomName(
                lectureSession.getClassRoom() != null ? lectureSession.getClassRoom().getClassRoomName() : null
        );
        responseDto.setDateOfLecture(lectureSession.getDateOfSession());
        responseDto.setSubjectNameAndCode(
                lectureSession.getSubject().getSubjectName() + "(" + lectureSession.getSubject().getSubjectCode() + ")"
        );

        var entries = new ArrayList<com.classtrack.dto.response.AttendanceEntryDto>();
        for (var a : attendanceRepository.findByLectureSession(lectureSession)) {
            var e = new com.classtrack.dto.response.AttendanceEntryDto();
            e.setStudentName(a.getStudent().getName());
            e.setRollNumber(a.getStudent().getRollNumber());
            e.setStatus(a.getAttendaceStatus());
            entries.add(e);
        }
        entries.sort(Comparator.comparingInt(com.classtrack.dto.response.AttendanceEntryDto::getRollNumber));
        responseDto.setAttendanceResult(entries);
        return responseDto;
    }

    @Override
    public List<TeacherReportDto> getClassWiseReports(UUID teacherId) {
        var schedules = scheduleRepository.findAllByTeacher(
                teacherRepository.findById(teacherId).orElseThrow(() -> new RuntimeException("teacher not found"))
        );

        // Deduplicate: teachers can have multiple routine entries (day/time) for the same
        // classRoom+subject. We want ONE report row per classRoom+subject.
        java.util.Map<String, TeacherReportDto> byClassAndSubject = new java.util.HashMap<>();
        java.util.Map<String, Long> bestScheduleCountByKey = new java.util.HashMap<>();

        for (Schedule s : schedules) {
            Long classRoomId = s.getClassRoom() != null ? s.getClassRoom().getId() : null;
            Long subjectId = s.getSubject() != null ? s.getSubject().getId() : null;
            String key = String.valueOf(classRoomId) + "|" + String.valueOf(subjectId);

            long scheduleCount = lectureSessionRepository.countByScheduleId(s.getId());

            TeacherReportDto dto = byClassAndSubject.get(key);
            if (dto == null) {
                dto = new TeacherReportDto();
                dto.setScheduleId(s.getId()); // representative scheduleId for fetching details
                dto.setClassRoomName(s.getClassRoom() != null ? s.getClassRoom().getClassRoomName() : null);
                dto.setSubjectName(s.getSubject() != null ? s.getSubject().getSubjectName() : null);
                dto.setSubjectCode(s.getSubject() != null ? s.getSubject().getSubjectCode() : null);
                dto.setClassesDone(0);
                byClassAndSubject.put(key, dto);
                bestScheduleCountByKey.put(key, scheduleCount);
            }

            dto.setClassesDone(dto.getClassesDone() + scheduleCount);

            // Prefer a scheduleId that has the most sessions, so "Details" isn't misleading.
            long best = bestScheduleCountByKey.getOrDefault(key, 0L);
            if (scheduleCount > best) {
                dto.setScheduleId(s.getId());
                bestScheduleCountByKey.put(key, scheduleCount);
            }
        }

        List<TeacherReportDto> dtos = new ArrayList<>(byClassAndSubject.values());
        dtos.sort(Comparator
                .comparing(TeacherReportDto::getClassRoomName, Comparator.nullsLast(String::compareTo))
                .thenComparing(TeacherReportDto::getSubjectCode, Comparator.nullsLast(String::compareTo)));
        return dtos;
    }

    @Override
    public List<TeacherReportStudentDto> getReportDetails(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("schedule not found"));

        Subject subject = schedule.getSubject();
        var classRoom = schedule.getClassRoom();

        List<StudentSubjectAttendanceSummary> summaries = attendanceSummaryRepository
                .findByClassRoomAndSubject(classRoom, subject);

        List<TeacherReportStudentDto> dtos = new ArrayList<>();
        for (StudentSubjectAttendanceSummary s : summaries) {
            TeacherReportStudentDto dto = new TeacherReportStudentDto();
            dto.setRollNumber(s.getStudent().getRollNumber());
            dto.setStudentName(s.getStudent().getName());
            dto.setClassesHeld(s.getClassesHeld());
            dto.setClassesAttended(s.getClassesAttended());

            if (s.getClassesHeld() == null || s.getClassesHeld() == 0) {
                dto.setPercentage(0L);
            } else {
                dto.setPercentage((s.getClassesAttended() * 100) / s.getClassesHeld());
            }
            dtos.add(dto);
        }
        dtos.sort(Comparator.comparingInt(TeacherReportStudentDto::getRollNumber));
        return dtos;
    }
}
