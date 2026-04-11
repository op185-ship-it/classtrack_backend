package com.classtrack.controller;

import com.classtrack.service.StudentImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Accepts student face images and forwards them to the Flask ML service
 * for embedding generation and local storage.
 *
 * POST /api/students/{rollNumber}/images
 *   Form-data:
 *     images  – 10 to 15 face images (jpg/png)
 */
@RestController
@RequestMapping("/api/students")
public class StudentImageController {

    private final StudentImageService studentImageService;

    public StudentImageController(StudentImageService studentImageService) {
        this.studentImageService = studentImageService;
    }

    @PostMapping(value = "/{rollNumber}/images", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, Object>> uploadStudentImages(
            @PathVariable Integer rollNumber,
            @RequestPart("images") List<MultipartFile> images) {

        if (images == null || images.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "At least one image is required"));
        }
        if (images.size() > 15) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Maximum 15 images allowed"));
        }

        Map<String, Object> result = studentImageService.registerStudentImages(rollNumber, images);
        return ResponseEntity.ok(result);
    }
}