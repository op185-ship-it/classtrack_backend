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
import java.util.Map;
import java.util.UUID;

/**
 * Sends the group photo + sessionId to Flask /process-attendance.
 * Flask will run face recognition and then call back to POST /attendance.
 */
@Service
public class AttendanceProcessService {

    private static final Logger log = LoggerFactory.getLogger(AttendanceProcessService.class);

    @Value("${flask.service.url:http://localhost:5000}")
    private String flaskBaseUrl;

    private final RestTemplate restTemplate;

    public AttendanceProcessService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Forward group photo to Flask for face-recognition processing.
     *
     * @param sessionId  UUID of the attendance session
     * @param groupPhoto classroom photo uploaded by the teacher
     * @return response body from Flask
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> triggerFaceRecognition(UUID sessionId, MultipartFile groupPhoto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("sessionId", sessionId.toString());

        try {
            byte[] bytes    = groupPhoto.getBytes();
            String filename = groupPhoto.getOriginalFilename() != null
                    ? groupPhoto.getOriginalFilename() : "group.jpg";

            ByteArrayResource resource = new ByteArrayResource(bytes) {
                @Override
                public String getFilename() { return filename; }
            };
            body.add("groupPhoto", resource);

        } catch (IOException e) {
            log.error("Failed to read group photo: {}", e.getMessage());
            throw new RuntimeException("Failed to read group photo", e);
        }

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        log.info("Sending group photo for sessionId={} to Flask /process-attendance", sessionId);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                flaskBaseUrl + "/process-attendance",
                requestEntity,
                Map.class
        );

        log.info("Flask completed attendance for sessionId={}, status={}", sessionId, response.getStatusCode());
        return response.getBody();
    }
}