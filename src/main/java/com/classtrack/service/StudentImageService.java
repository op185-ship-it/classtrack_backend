package com.classtrack.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Forwards student face images to the Flask ML service
 * which generates + stores the averaged FaceNet512 embedding.
 */
@Service
public class StudentImageService {

    private static final Logger log = LoggerFactory.getLogger(StudentImageService.class);

    @Value("${flask.service.url:http://localhost:5000}")
    private String flaskBaseUrl;

    private final RestTemplate restTemplate;

    public StudentImageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Sends all images for a student to Flask /register-student as multipart/form-data.
     *
     * @param rollNumber student roll number
     * @param images     list of uploaded image files (10–15 recommended)
     * @return response body from Flask (parsed as generic Map)
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> registerStudentImages(Integer rollNumber, List<MultipartFile> images) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("rollNumber", rollNumber.toString());

        for (MultipartFile file : images) {
            try {
                // Wrap bytes in a named resource so Spring sets filename in the part
                byte[] bytes = file.getBytes();
                String originalName = file.getOriginalFilename() != null
                        ? file.getOriginalFilename() : "image.jpg";

                ByteArrayResource resource = new ByteArrayResource(bytes) {
                    @Override
                    public String getFilename() { return originalName; }
                };
                body.add("images", resource);

            } catch (IOException e) {
                log.error("Could not read bytes from file {}: {}", file.getOriginalFilename(), e.getMessage());
                throw new RuntimeException("Failed to read uploaded file: " + file.getOriginalFilename(), e);
            }
        }

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        log.info("Forwarding {} images for rollNumber={} to Flask", images.size(), rollNumber);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                flaskBaseUrl + "/register-student",
                requestEntity,
                Map.class
        );

        log.info("Flask response for rollNumber={}: status={}", rollNumber, response.getStatusCode());
        return response.getBody();
    }
}