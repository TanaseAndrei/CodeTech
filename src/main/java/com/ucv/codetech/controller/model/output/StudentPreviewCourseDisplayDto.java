package com.ucv.codetech.controller.model.output;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class StudentPreviewCourseDisplayDto extends RepresentationModel<StudentPreviewCourseDisplayDto> {

    private Long enrolledCourseId;

    private String name;

    private int numberOfCompletedLectures;

    private int numberOfLectures;

    private boolean isCourseCompleted;

    private String coverImageName;
}
