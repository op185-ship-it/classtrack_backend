package com.classtrack.controller;

import com.classtrack.service.AttendanceProcessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

/**
 * Teacher uploads a group / classroom photo to trigger face-recognition attendance.
 *
 * POST /api/sessions/{sessionId}/process-attendance
 *   Form-data:
 *     groupPhoto – one classroom photo containing all students
 *
 * Spring Boot forwards the photo to Flask, which:
 *   1. Detects every face in the photo
 *   2. Matches each face against stored embeddings
 *   3. Calls  POST /attendance  on this same Spring Boot app
 *
 * The teacher receives the Flask response (which mirrors the Spring Boot save result).
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/sessions")
public class AttendanceProcessController {

    private final AttendanceProcessService attendanceProcessService;

    public AttendanceProcessController(AttendanceProcessService attendanceProcessService) {
        this.attendanceProcessService = attendanceProcessService;
    }

    @PostMapping(value = "/{sessionId}/process-attendance", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, Object>> processAttendance(
            @PathVariable UUID sessionId,
            @RequestPart("groupPhoto") MultipartFile groupPhoto) {

        if (groupPhoto == null || groupPhoto.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "groupPhoto is required"));
        }

        Map<String, Object> result = attendanceProcessService.triggerFaceRecognition(sessionId, groupPhoto);
        return ResponseEntity.ok(result);
    }
}