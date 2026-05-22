package com.classtrack.service;

import java.util.List;
import java.util.Map;

import com.classtrack.dto.SubjectDto;
import com.classtrack.dto.response.SubjectResponseDto;
import org.jspecify.annotations.Nullable;

public interface SubjectService {

	Map<String, String> createSubject(String departmentCode,Integer semester,SubjectDto dto);

    @Nullable
    List<SubjectResponseDto> getAllSubjects();
}
