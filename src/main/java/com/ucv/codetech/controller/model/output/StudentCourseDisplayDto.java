package com.ucv.codetech.controller.model.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
public class StudentCourseDisplayDto extends RepresentationModel<StudentCourseDisplayDto> {

    private Long enrolledCourseId;

    private String name;

    private int numberOfCompletedLectures;

    private int numberOfLectures;

    private boolean isCourseCompleted;

    private String coverImageName;
}
