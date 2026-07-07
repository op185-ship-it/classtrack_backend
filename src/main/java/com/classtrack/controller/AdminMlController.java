package com.classtrack.controller;

import com.classtrack.service.AdminMlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admins/ml")
public class AdminMlController {

    private final AdminMlService adminMlService;

    public AdminMlController(AdminMlService adminMlService) {
        this.adminMlService = adminMlService;
    }

    @PostMapping("/train-model")
    public ResponseEntity<Map<String, Object>> trainModel() {
        Map<String, Object> result = adminMlService.trainModelAndGenerateEmbeddings();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

