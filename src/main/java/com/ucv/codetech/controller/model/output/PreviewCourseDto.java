package com.ucv.codetech.controller.model.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;


@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class PreviewCourseDto extends RepresentationModel<PreviewCourseDto> {

    private Long courseId;

    @JsonIgnore
    private Long enrolledCourseId;

    private String name;

    private String instructorName;

    private int enrolledStudents;

    private int numberOfLectures;

    private int numberOfComments;

    private String difficulty;

    private boolean isAlreadyEnrolled = false;

    private String coverImageName;
}
