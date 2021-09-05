package com.ucv.codetech.controller.model.output;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class StudentCertificationDisplayDto extends RepresentationModel<StudentCertificationDisplayDto> {

    private String studentName;

    private String courseName;

    private String certificationDate;

    private Long courseId;
}