package com.ucv.codetech.facade.converter;

import com.ucv.codetech.controller.model.output.StudentCertificationDisplayDto;
import com.ucv.codetech.model.Certification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CertificationConverter {

    public List<StudentCertificationDisplayDto> entitiesToStudentCertificationDisplayDtos(List<Certification> certifications) {
        return certifications
                .stream()
                .map(this::entityToStudentCertificationDisplayDto)
                .collect(Collectors.toList());
    }

    public StudentCertificationDisplayDto entityToStudentCertificationDisplayDto(Certification certification) {
        StudentCertificationDisplayDto studentCertificationDisplayDto = new StudentCertificationDisplayDto();
        studentCertificationDisplayDto.setCertificationDate(certification.getCertificationDate());
        studentCertificationDisplayDto.setStudentName(certification.getStudent().getUsername());
        studentCertificationDisplayDto.setCourseName(certification.getCourse().getName());
        studentCertificationDisplayDto.setCourseId(certification.getCourse().getId());
        return studentCertificationDisplayDto;
    }
}
