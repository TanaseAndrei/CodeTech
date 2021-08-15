package com.ucv.codetech.controller.model.output;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class InstructorPreviewCourseDisplayDto extends RepresentationModel<InstructorPreviewCourseDisplayDto> {

    private Long courseId;

    private String name;

    private int numberOfLectures;

    private int numberOfComments;

    private int numberOfEnrolledStudents;

    private String coverImage;
}
