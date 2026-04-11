package com.classtrack.service;

import java.util.Map;

import com.classtrack.dto.SubjectDto;

public interface SubjectService {

	Map<String, String> createSubject(String departmentCode,Integer semester,SubjectDto dto);
}
