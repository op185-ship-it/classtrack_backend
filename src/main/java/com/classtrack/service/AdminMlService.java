package com.classtrack.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AdminMlService {

    private static final Logger log = LoggerFactory.getLogger(AdminMlService.class);

    @Value("${flask.service.url:http://localhost:5000}")
    private String flaskBaseUrl;

    private final RestTemplate restTemplate;

    public AdminMlService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> trainModelAndGenerateEmbeddings() {
        String url = flaskBaseUrl + "/train-model";
        log.info("Triggering Flask training at {}", url);
        ResponseEntity<Map> resp = restTemplate.postForEntity(url, null, Map.class);
        return resp.getBody();
    }
}

