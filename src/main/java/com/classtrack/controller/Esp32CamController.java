package com.classtrack.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Simple proxy endpoint for ESP32-CAM snapshot capture.
 *
 * Browser-to-ESP32 fetch often fails due to CORS. This endpoint fetches the image
 * server-side and returns it to the frontend.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/esp32")
public class Esp32CamController {

    private final RestTemplate restTemplate;

    @Value("${esp32.cam.url:http://192.168.1.4/capture}")
    private String esp32CamUrl;

    public Esp32CamController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/capture")
    public ResponseEntity<byte[]> capture() {
        ResponseEntity<byte[]> resp = restTemplate.getForEntity(esp32CamUrl, byte[].class);
        MediaType contentType = resp.getHeaders().getContentType();
        if (contentType == null) {
            contentType = MediaType.IMAGE_JPEG;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(contentType);
        headers.setCacheControl("no-store");
        return new ResponseEntity<>(resp.getBody(), headers, HttpStatus.OK);
    }
}

