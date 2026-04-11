package com.classtrack.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.classtrack.dto.SubjectDto;
import com.classtrack.entity.Department;
import com.classtrack.entity.Subject;
import com.classtrack.repository.DepartmentRepository;
import com.classtrack.repository.SubjectRepository;

import jakarta.transaction.Transactional;

@Service
public class SubjectServiceImpl implements SubjectService {
	
	DepartmentRepository departmentRepository;
	SubjectRepository subjectRepository;
	 

	public SubjectServiceImpl(DepartmentRepository departmentRepository, SubjectRepository subjectRepository) {
		super();
		this.departmentRepository = departmentRepository;
		this.subjectRepository = subjectRepository;
	}



	@Override
	@Transactional
	public Map<String, String> createSubject(String departmentCode, Integer semester, SubjectDto dto) {

		Subject subject = new Subject();
		subject.setSubjectCode(dto.getSubjectCode());
		subject.setSubjectName(dto.getSubjectName());
		subject.setSemester(semester);
		
		Department d = departmentRepository.findByDepartmentCode(departmentCode)
				.orElseThrow(()-> new RuntimeException("Department Not found"));
		d.addSubject(subject);
		departmentRepository.save(d);
//		subjectRepository.save(subject);
		HashMap<String, String> dtoHm = new HashMap<>();
//		System.out.println(d.getSubjects());
		
		for(Subject sub : d.getSubjects()) {
			dtoHm.put(sub.getSubjectCode(), sub.getSubjectName());
		}
		
		return dtoHm;
	}

}
